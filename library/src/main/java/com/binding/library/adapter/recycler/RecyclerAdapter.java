package com.binding.library.adapter.recycler;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.binding.library.adapter.AdapterHandle;
import com.binding.library.adapter.AdapterType;
import com.binding.library.adapter.IEntityAdapter;
import com.binding.library.adapter.IRecyclerAdapter;
import com.binding.library.model.ViewInflate;
import com.binding.library.model.ViewInflateRecycler;
import com.binding.library.model.inter.SpanSize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：14:47
 * modify developer：  admin
 * modify time：14:47
 * modify remark：
 *
 * @version 2.0
 */

public class RecyclerAdapter<E extends ViewInflateRecycler>
        extends RecyclerView.Adapter<RecyclerHolder<ViewInflate>>
        implements IRecyclerAdapter<E> {
    private final List<E> holderList = new ArrayList<>();
    private final List<ViewInflate> totalList = new ArrayList<>();
    private final HashSet<E> checkList = new HashSet<>();
    private final SparseArray<ViewInflate> sparseArray = new SparseArray<>();
    private AtomicBoolean refresh = new AtomicBoolean(false);

    private final int max;
    private IEntityAdapter<E> iEntityAdapter = this;
    private final ArrayBlockingQueue<E> queue;
    private int count;

    public RecyclerAdapter() {
        this(0);
    }

    public RecyclerAdapter(int max) {
        this.max = max;
        if (max > 0) {
            queue = new ArrayBlockingQueue<>(max);
        } else {
            queue = new ArrayBlockingQueue<>(1);
        }
    }

    @Override
    public void setIEntityAdapter(IEntityAdapter<E> iEntityAdapter) {
        this.iEntityAdapter = iEntityAdapter;
    }

    @Override
    public RecyclerHolder<ViewInflate> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerHolder<ViewInflate>(parent, sparseArray.get(viewType));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    ViewInflate e = totalList.get(position);
                    int spanSize = 1;
                    if (e instanceof SpanSize) spanSize = ((SpanSize) e).getSpanSize();
                    return spanSize;
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
    }

    @Override
    public void onBindViewHolder(RecyclerHolder<ViewInflate> holder, int position) {
        ViewInflate e = totalList.get(position);
        holder.executePendingBindings(e, iEntityAdapter);
    }

    @Override
    public int getItemViewType(int position) {
        ViewInflate e = totalList.get(position);
        int viewType = e.getModelIndex();
        sparseArray.put(viewType, e);
        return viewType;
    }

    @Override
    public int getItemCount() {
        return count == 0 || count > totalList.size() ? totalList.size() : count;
    }

    @Override
    public boolean setList(int position, List<E> es, @AdapterHandle int type) {
        if (es == null) return false;
        switch (type) {
            case AdapterType.add:notifyItemRangeInserted(addAll(position,es),es.size());break;
            case AdapterType.refresh:
                if (size() == 0) notifyItemRangeInserted(addAll(position,es),es.size());
                else refresh(es);break;
            case AdapterType.remove:removeAllNotify(es);break;
            case AdapterType.select:for (E e : es) select(e);break;
            case AdapterType.set:break;
            case AdapterType.no:
            default:return false;
        }
        return true;
    }


    @Override
    public boolean setEntity(int position, E e, int type) {
        switch (type) {
            case AdapterType.add:notifyItemInserted(add(position, e));break;
            case AdapterType.remove:removeNotify(position, e);break;
            case AdapterType.set:notifyItemChanged(set(position,e));break;
            case AdapterType.select:select(e);break;
            case AdapterType.refresh:notifyDataSetChanged();break;
            case AdapterType.no:
            default:return false;
        }
        return true;
    }


    private void removeNotify(int position, E e) {
        int p = remove(position, e);
        if (p >= 0) notifyItemRemoved(p);
    }

    private int remove(int position, E e) {
        boolean pValid = position == NO_POSITION || position < -1 || position > totalList.size();
        int p;
        if (pValid&&e == null) return -1;
        if(e == null){
            E ei = holderList.get(position);
            p = totalList.indexOf(ei);
            holderList.remove(ei);
            totalList.remove(ei);
        }else{
            p = totalList.indexOf(e);
            totalList.remove(e);
            holderList.remove(e);
        }
        return p;
    }


    @Override
    public boolean setEntity(int position, E e, int type, boolean done) {
        position = position == NO_POSITION ? holderList.indexOf(e) : position;
        boolean b;
        if (iEntityAdapter == this) {
            b = setEntity(position, e, type);
        } else {
            b = iEntityAdapter.setEntity(position, e, type, done);
            if (done) b = setEntity(position, e, type);
        }
        return b;
    }


    public void checkAll(boolean check) {
        for (E e : holderList) e.check(check);
    }

    private void select(E e) {
        if (max > 0) {
            boolean contains = queue.contains(e);
            if (!contains) {
                if (queue.size() == max)
                    queue.poll().check(false);
                e.check(true);
                queue.offer(e);
            } else {
                e.check(false);
            }
        } else {
            boolean c = checkList.contains(e);
            boolean s = c ? checkList.remove(e) : checkList.add(e);
            e.check(s && !c);
        }
    }

    public Collection<E> getChecked() {
        return max > 0 ? queue : checkList;
    }


    @SuppressWarnings("unchecked")
    public void refresh(List<E> es) {
        if (es != null && !es.isEmpty() && !refresh.get()) {
            refresh.set(true);
            Observable.fromArray(es)
                    .map(s -> DiffUtil.calculateDiff(new DiffUtilCallback<>(holderList, s)))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(diffResult -> {
                                diffResult.dispatchUpdatesTo(this);
                                clear();
                                addAll(0,es);
                                refresh.set(false);
                            }
                    );
        }
    }

    @Override
    public List<E> getList() {
        return holderList;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int size() {
        return holderList.size();
    }

    public void addInflate(int position,ViewInflate inflate){
        totalList.add(position,inflate);
    }

    public void removeInflate(ViewInflate inflate){
        int position = totalList.indexOf(inflate);
        if(holderList.contains(inflate))holderList.remove(inflate);
        totalList.remove(inflate);
        notifyItemRemoved(position);
    }

    public void remove(int position){
        ViewInflate ti = totalList.get(position);
        if(holderList.contains(ti))holderList.remove(ti);
        totalList.remove(position);
        notifyItemRemoved(position);
    }

    public void clean(){
        totalList.clear();
        holderList.clear();
    }
    
    private void clear() {
        totalList.removeAll(holderList);
        holderList.clear();
    }

 
    private int add(int position, E e) {
        int p;
        if (holderList.isEmpty()) {
            holderList.add(e);
            p = totalList.size();
            totalList.add(e);
        } else if (position == NO_POSITION) {
            holderList.add(e);
            E last = holderList.get(holderList.size() - 1);
            p = totalList.indexOf(last) + 1;
            totalList.add(p, e);
        } else {
            holderList.add(position, e);
            E first = holderList.get(0);
            p = totalList.indexOf(first)+position;
            totalList.add(p, e);
            return p;
        }
        return p;
    }


    private int set(int position, E e) {
        E ie = holderList.get(position);
        int p = totalList.indexOf(ie);
        totalList.set(p,e);
        holderList.set(position,e);
        return p;
    }


    private int addAll(int position,List<E> es) {
        int p;
        if(holderList.isEmpty()){
            holderList.addAll(es);
            totalList.addAll(es);
            return 0;
        }
        if(position>=holderList.size()||position<0){
            E ie = holderList.get(size()-1);
            p = totalList.indexOf(ie)+1;
            totalList.addAll(p,es);
            holderList.add(position,ie);
        }else{
            E ie = holderList.get(position);
            p = totalList.indexOf(ie)+1;
            holderList.addAll(position,es);
            totalList.addAll(p,es);
        }
        return p;
    }

    
    private void removeAllNotify(List<E> es) {
        if(es == null||es.isEmpty())return;
        int p = totalList.indexOf(es.get(0));
        holderList.removeAll(es);
        totalList.removeAll(es);
        notifyItemRangeRemoved(p,es.size());
    }

//    private void addAllNotify(int position,List<E> es) {
//        notifyItemRangeInserted(addAll(position,es), es.size());
//    }
}
