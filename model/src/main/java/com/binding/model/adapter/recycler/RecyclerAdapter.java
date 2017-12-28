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
import com.binding.model.model.inter.Inflate;
import com.binding.model.model.inter.SpanSize;

import java.util.ArrayList;
import java.util.List;
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
        extends RecyclerBaseAdapter<E>
        implements IRecyclerAdapter<E> {


    public RecyclerAdapter() {
        this.iEventAdapter = this;
    }

    @Override
    public void setIEventAdapter(IEventAdapter<E> iEntityAdapter) {
        this.iEventAdapter = iEntityAdapter;
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
    public boolean setEntity(int position, E e, int type, View view) {
        switch (type) {
            case AdapterType.add:
                return addToAdapter(position, e, holderList);
            case AdapterType.remove:
                return removeToAdapter(position, e, holderList);
            case AdapterType.set:
                return setToAdapter(position, e, holderList);
            case AdapterType.move:
                return moveToAdapter(position, e, holderList);
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
    public boolean setList(int position, List<E> es, @AdapterHandle int type) {
        if (es == null || es.isEmpty()) return false;
        switch (type) {
            case AdapterType.refresh:
                return refreshListAdapter(position, es, holderList);
            case AdapterType.add:
                return addListAdapter(position, es, holderList);
            case AdapterType.remove:
                return removeListAdapter(position, es, holderList);
            case AdapterType.set:
                return setListAdapter(position, es, holderList);
            case AdapterType.move:
                return moveListAdapter(position, es, holderList);
            case AdapterType.no:
            case AdapterType.select:
            case AdapterType.onClick:
            case AdapterType.onLongClick:
            default:
                return false;
        }
    }

    public final boolean setToAdapter(int position, E e) {
        return setToAdapter(position, e, holderList);
    }

    protected boolean setToAdapter(int position, E e, List<E> holderList) {
        if (position >= 0 && position < holderList.size()) {
            holderList.set(position, e);
            notifyItemChanged(position);
            return true;
        }
        return false;
    }

    public final boolean addToAdapter(int position, E e) {
        return addToAdapter(position, e, holderList);
    }

    protected boolean addToAdapter(int position, E e, List<E> holderList) {
        if (position >= holderList.size() || position < 0) {
            position = holderList.size();
            holderList.add(e);
        } else holderList.add(position, e);
        notifyItemInserted(position);
        return true;
    }


    public final boolean removeToAdapter(int position, E e) {
        return removeToAdapter(position, e, holderList);
    }

    protected boolean removeToAdapter(int position, E e, List<E> holderList) {
        if (holderList.contains(e)) position = holderList.indexOf(e);
        else if (position < 0 || position >= holderList.size()) return false;
        holderList.remove(position);
        notifyItemRemoved(position);
        return true;
    }

    public final boolean setListAdapter(int position, List<E> es) {
        return setListAdapter(position, es, holderList);
    }

    protected boolean setListAdapter(int position, List<E> es, List<E> holderList) {
        if (position >= holderList.size()) return addListAdapter(position, es, holderList);
        else if (position + es.size() >= holderList.size())
            return refreshListAdapter(position, es, holderList);
        else if (position > 0) {
            for (int i = 0; i < es.size(); i++) setToAdapter(position, es.get(i), holderList);
            return true;
        }
        return false;
    }

    public final boolean removeListAdapter(int position, List<E> es) {
        return removeListAdapter(position, es, holderList);
    }

    protected boolean removeListAdapter(int position, List<E> es, List<E> holderList) {
        int rang = isRang(position, es, holderList);
        if (rang>=0) {
            holderList.removeAll(es);
            notifyItemRangeRemoved(position, es.size());
            return true;
        } else {
            for (E e : es) removeToAdapter(0, e, holderList);
            return false;
        }
    }

    public final boolean addListAdapter(int position, List<E> es) {
        return addListAdapter(position, es, holderList);
    }

    protected boolean addListAdapter(int position, List<E> es, List<E> holderList) {
        if (position < 0 || position >= holderList.size()) {
            position = holderList.size();
            holderList.addAll(es);
        } else holderList.addAll(position, es);
        notifyItemRangeInserted(position, es.size());
        return true;
    }

    public final boolean refreshListAdapter(int position, List<E> es) {
        return refreshListAdapter(position, es, holderList);
    }

    protected boolean refreshListAdapter(int position, List<E> es, List<E> holderList) {
        List<E> l;
        if (position >= 0 && position < holderList.size()) {
            l = holderList.subList(0, position);
            l.addAll(es);
        } else if (holderList.size() == 0) {
            return addListAdapter(position, es, holderList);
        } else l = es;
        refresh(l, holderList);
        return true;
    }


    private AtomicBoolean refresh = new AtomicBoolean(false);

    @SuppressWarnings("unchecked")
    private void refresh(List<E> es, List<E> holderList) {
        if (!refresh.get()) {
            refresh.set(true);
            Observable.fromArray(es)
                    .map(s -> DiffUtil.calculateDiff(new DiffUtilCallback<>(holderList, s)))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(diffResult -> {
                                diffResult.dispatchUpdatesTo(this);
                                holderList.clear();
                                holderList.addAll(es);
                                refresh.set(false);
                            }
                    );
        }
    }


}
