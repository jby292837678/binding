package com.binding.model.adapter.recycler;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.binding.model.adapter.AdapterHandle;
import com.binding.model.adapter.AdapterType;
import com.binding.model.adapter.IEventAdapter;
import com.binding.model.adapter.IRecyclerAdapter;
import com.binding.model.model.ViewInflate;
import com.binding.model.model.ViewInflateRecycler;
import com.binding.model.model.inter.Inflate;
import com.binding.model.model.inter.Recycler;
import com.binding.model.model.inter.SpanSize;

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

public class RecyclerAdapter<E extends Inflate>
        extends RecyclerView.Adapter<RecyclerHolder<E>>
        implements IRecyclerAdapter<E> {
    private final List<E> holderList = new ArrayList<>();
    private final SparseArray<E> sparseArray = new SparseArray<>();
    private AtomicBoolean refresh = new AtomicBoolean(false);
    protected IEventAdapter<E> iEventAdapter = this;
    private int count;

    @Override
    public void setIEventAdapter(IEventAdapter<E> iEntityAdapter) {
        this.iEventAdapter = iEntityAdapter;
    }

    @Override
    public RecyclerHolder<E> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerHolder<>(parent, sparseArray.get(viewType));
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
                    E e = holderList.get(position);
                    int spanSize = 1;
                    if (e instanceof SpanSize) spanSize = ((SpanSize) e).getSpanSize();
                    return spanSize;
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
    }

    @Override
    public void onBindViewHolder(RecyclerHolder<E> holder, int position) {
        holder.executePendingBindings( holderList.get(position), iEventAdapter);
    }

    @Override
    public int getItemViewType(int position) {
        E e = holderList.get(position);
        int viewType = e.getModelIndex();
        sparseArray.put(viewType, e);
        return viewType;
    }

    @Override
    public int getItemCount() {
        return holderList.size();
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
            case AdapterType.set:break;
            case AdapterType.no:
            default:return false;
        }
        return true;
    }


    @Override
    public boolean setEntity(int position, E e, int type, View view){
        switch (type) {
            case AdapterType.add:notifyItemInserted(add(position, e));break;
            case AdapterType.remove:removeNotify(position, e);break;
            case AdapterType.set:notifyItemChanged(set(position,e));break;
            case AdapterType.refresh:notifyDataSetChanged();break;
            case AdapterType.no:
            default:return false;
        }
        return true;
    }


    private void removeNotify(int position, E e) {
        holderList.remove(position);
        notifyItemRemoved(position);
    }


    @SuppressWarnings("unchecked")
    public void refresh(List<E> es) {
        if (es != null && !es.isEmpty() && !refresh.get()) {
            if(holderList.isEmpty()){
                addAll(0,es);
            }else{
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
    }

    @Override
    public List<E> getList() {
        return holderList;
    }

    @Override
    public int size() {
        return holderList.size();
    }

    public void remove(int position){
        holderList.remove(position);
        notifyItemRemoved(position);
    }


    private void clear() {
        holderList.clear();
    }


    private int add(int position, E e) {
        holderList.add(position,e);
        return position;
    }


    public int set(int position, E e) {
        holderList.set(position,e);
        return position;
    }

    public int addAll(int position,List<E> es) {
        holderList.addAll(position,es);
        return position;
    }


    public void removeAllNotify(List<E> es) {
        if(es == null||es.isEmpty())return;
        int p = holderList.indexOf(es.get(0));
        holderList.removeAll(es);
        notifyItemRangeRemoved(p,es.size());
    }

}
