package com.binding.model.adapter.recycler;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.binding.model.adapter.AdapterHandle;
import com.binding.model.adapter.AdapterType;
import com.binding.model.adapter.IEventAdapter;
import com.binding.model.adapter.IListAdapter;
import com.binding.model.adapter.IModelAdapter;
import com.binding.model.adapter.IRecyclerAdapter;
import com.binding.model.model.inter.Inflate;
import com.binding.model.util.ReflectUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.binding.model.util.BaseUtil.containsList;

/**
 * Created by arvin on 2017/12/21.
 */

@SuppressWarnings("unchecked")
public abstract class RecyclerBaseAdapter<E extends Inflate, I extends Inflate>
        extends RecyclerView.Adapter<RecyclerHolder<E>> implements IModelAdapter<E>, IEventAdapter<I>, IListAdapter<E> {

    private final IEventAdapter<I> iEventAdapter = this;
    private final List<E> holderList = new ArrayList<>();
    private final SparseArray<E> sparseArray = new SparseArray<>();
    private int count;
    protected final List<IEventAdapter<I>> eventAdapters = new ArrayList<>();
    private Disposable disposable;

    public RecyclerBaseAdapter() {
        eventAdapters.add(iEventAdapter);
    }

    @Override
    public RecyclerHolder<E> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerHolder<>(parent, sparseArray.get(viewType));
    }

    @Override
    public void onBindViewHolder(RecyclerHolder<E> holder, int position) {
        E e = holderList.get(position);
        holder.executePendingBindings(position, e, iEventAdapter);
    }

    @Override
    public int getItemViewType(int position) {
        E e = holderList.get(position);
        int viewType = e.getLayoutId();
        sparseArray.put(viewType, e);
        return viewType;
    }

    @Override
    public boolean setEntity(int position, I i, int type, View view) {
        for (IEventAdapter<I> eventAdapter : eventAdapters) {
            if (eventAdapter instanceof IModelAdapter) {
                return setISEntity((IModelAdapter<I>) eventAdapter, position, i, type, view);
            } else if (eventAdapter.setEntity(position, i, type, view)) return true;
        }
        return false;
    }

    abstract boolean setISEntity(IModelAdapter<I> eventAdapter, int position, I i, int type, View view);

    protected boolean isDisposed(){
        return disposable==null||disposable.isDisposed();
    }

    public boolean setIEntity(int position, E e, int type, View v) {
        if (e == null) return false;
        switch (type) {
            case AdapterType.add:
                return addToAdapter(position, e);
            case AdapterType.remove:
                return removeToAdapter(position, e);
            case AdapterType.set:
                return setToAdapter(position, e);
            case AdapterType.move:
                return moveToAdapter(position, e);
            case AdapterType.select:
            case AdapterType.refresh:
            case AdapterType.no:
            case AdapterType.onClick:
            case AdapterType.onLongClick:
            default:
                return false;
        }
    }

    @Override
    public boolean setList(int position, List<? extends E> es, @AdapterHandle int type) {
        if (es == null) return false;
        switch (type) {
            case AdapterType.refreshAsync:
                return refresh(position,es);
            case AdapterType.refresh:
                return refreshListAdapter(position, es);
            case AdapterType.add:
                return addListAdapter(position, es);
            case AdapterType.remove:
                return removeListAdapter(position, es);
            case AdapterType.set:
                return setListAdapter(position, es);
            case AdapterType.move:
                return moveListAdapter(position, es);
            case AdapterType.no:
            case AdapterType.select:
            case AdapterType.onClick:
            case AdapterType.onLongClick:
            default:
                return false;
        }
    }

    public final boolean setListAdapter(int position, List<?extends E> es) {
        if(!isDisposed())return false;
        if (position >= holderList.size()) return addListAdapter(position, es);
        else if (position + es.size() >= holderList.size())
            return refreshListAdapter(position, es);
        else if (position > 0) {
            for (int i = 0; i < es.size(); i++) setToAdapter(position, es.get(i));
            return true;
        }
        return false;
    }


    public final boolean removeListAdapter(int position, List<?extends E> es) {
        if(!isDisposed())return false;
        int rang = isRang(position, es, holderList);
        if (rang >= 0) {
            holderList.removeAll(es);
            notifyItemRangeRemoved(position, es.size());
            return true;
        } else {
            for (E e : es) removeToAdapter(0, e);
            return false;
        }
    }


    public final boolean setToAdapter(int position, E e) {
        if(!isDisposed())return false;
        if (containsList(position, holderList)) {
            e.setIEventAdapter(iEventAdapter);
            notifyItemChanged(position);
            holderList.set(position, e);
            return true;
        }
        return false;
    }


    public final boolean addToAdapter(int position, E e) {
        if(!isDisposed())return false;
        return addToAdapter(position, e, holderList);
    }

    public final boolean addToAdapter(int position, E e, List<E> holderList) {
        if(!isDisposed())return false;
        if (!containsList(position, holderList)) {
            position = holderList.size();
            notifyItemInserted(position);
            holderList.add(e);
        } else {
            notifyItemInserted(position);
            holderList.add(position, e);
        }
        return true;
    }

    public final boolean removeToAdapter(int position, E e) {
        if(!isDisposed())return false;
        if (holderList.contains(e)) position = holderList.indexOf(e);
        else if (!containsList(position, holderList)) return false;
        notifyItemRemoved(position);
        holderList.remove(position);
        return true;
    }

    public final boolean addListAdapter(int position, List<? extends E> es) {
        if(!isDisposed())return false;
        if (!containsList(position, holderList)) {
            position = holderList.size();
            holderList.addAll(es);
        } else holderList.addAll(position, es);
        notifyItemRangeInserted(position, es.size());
        return true;
    }


//    public List<E> refresh(int p, List<? extends E> es) {
//        holderList.clear();
//        holderList.addAll(es);
//        List<E> list = getAdapter().getList();
//        if (p == list.size() || list.isEmpty())
//            holderList.addAll(0, list);
//        if (containsList(p, list))
//            holderList.addAll(0, list.subList(0, p));
//        return holderList;
//    }


    private boolean refresh(int position,List<? extends E> es) {
        if(!isDisposed())return false;
        if(position == holderList.size()||holderList.isEmpty()) return addListAdapter(position,es);
        List<E> eList = new ArrayList<>();
        if(containsList(position,holderList)) eList.addAll(holderList.subList(0,position));
        eList.addAll(es);
        disposable = Observable.fromArray(eList)
                .map(s -> DiffUtil.calculateDiff(new DiffUtilCallback<>(holderList, s)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(diffResult -> {
                            diffResult.dispatchUpdatesTo(this);
                            holderList.clear();
                            holderList.addAll(eList);
                        }
                );
        return disposable.isDisposed();
    }


    public final boolean refreshListAdapter(int position, List<? extends E> es) {
        if(position == holderList.size()||holderList.isEmpty()) return addListAdapter(position,es);
        List<E> eList = new ArrayList<>();
        if(containsList(position,holderList)) eList.addAll(0,holderList.subList(0,position));
        eList.addAll(es);
        notifyDataSetChanged();
        holderList.clear();
        holderList.addAll(eList);
        return true;
    }

    @Override
    public int getItemCount() {
        return count == 0 || count > holderList.size() ? holderList.size() : count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public final boolean moveListAdapter(int position, List<? extends E> es) {
        if(!isDisposed())return false;
        for (int i = 0; i < es.size(); i++)
            moveToAdapter(position + i, es.get(i));
        return isRang(position, es, holderList) >= 0;
    }


    protected int isRang(int position, List<? extends E> es, List<? extends E> holderList) {
        if (es.isEmpty()) return -2;
        int rang = holderList.indexOf(es.get(0));
        for (int i = 0; i < es.size(); i++) {
            if (holderList.indexOf(es.get(i)) == position + i) continue;
            rang = -1;
            break;
        }
        return rang;
    }

    public final boolean moveToAdapter(int position, E e) {
        return moveToAdapter(position, e, holderList);
    }

    public final boolean moveToAdapter(int position, E e, List<E> holderList) {
        if (!isDisposed()||position < 0) return false;
        if (position >= holderList.size()) position = holderList.size() - 1;
        int from = holderList.indexOf(e);
        if (from != position && holderList.remove(e)) {
            notifyItemMoved(from, position);
            holderList.add(position, e);
            return true;
        }
        return false;
    }



    public void addEventAdapter(IEventAdapter<? extends I> eventAdapter) {
        eventAdapters.add(0, (IEventAdapter<I>) eventAdapter);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (!eventAdapters.contains(iEventAdapter))
            eventAdapters.add(iEventAdapter);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        eventAdapters.clear();
        if (disposable != null) disposable.dispose();
    }

    @Override
    public List<E> getList() {
        return holderList;
    }

    @Override
    public int size() {
        return holderList.size();
    }

    @Override
    public void clear() {
        int size = size();
        holderList.clear();
        notifyItemRangeRemoved(0, size);
    }

}
