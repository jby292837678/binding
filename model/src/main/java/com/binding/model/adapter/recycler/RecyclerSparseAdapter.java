package com.binding.model.adapter.recycler;

import android.support.v7.util.DiffUtil;
import android.view.View;

import com.binding.model.adapter.AdapterHandle;
import com.binding.model.adapter.AdapterType;
import com.binding.model.adapter.IEventAdapter;
import com.binding.model.adapter.IModelAdapter;
import com.binding.model.adapter.IRecyclerAdapter;
import com.binding.model.model.inter.Inflate;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
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
//@SuppressWarnings("unchecked")
public class RecyclerSparseAdapter<E extends Inflate>
        extends RecyclerBaseAdapter<Inflate>
        implements IRecyclerAdapter<E> {
    private final List<E> inflates = new ArrayList<>();
    private final TreeMap<Integer, Inflate> other = new TreeMap<>((integer, t1) -> integer - t1);
    private IEventAdapter<E> eventAdapter;

    public RecyclerSparseAdapter() {
        iEventAdapter = this;
    }

    @Override
    public void setIEventAdapter(IEventAdapter<E> iEntityAdapter) {
        this.iEventAdapter = iEntityAdapter;
    }

    @Override
    public List<E> getList() {
        return inflates;
    }

    @Override
    public int size() {
        return inflates.size();
    }

    @Override
    public void setEventAdapter(IEventAdapter<E> eventAdapter) {
        this.eventAdapter = eventAdapter;
    }

    @Override
    public boolean setEntity(int position, E e, int type, View view) {
        if(eventAdapter!=null&&eventAdapter.setEntity(position,e,type,view))return true;
        switch (type) {
            case AdapterType.add:
                return addToAdapter(position, e, inflates);
            case AdapterType.remove:
                return removeToAdapter(position, e, inflates);
            case AdapterType.set:
                return setToAdapter(position, e, inflates);
            case AdapterType.move:
                return moveItemToAdapter(position, e, inflates);
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
                return refreshListAdapter(position, es, inflates);
            case AdapterType.add:
                return addListAdapter(position, es, inflates);
            case AdapterType.remove:
                return removeListAdapter(position, es, inflates);
            case AdapterType.set:
                return setListAdapter(position, es, inflates);
            case AdapterType.move:
                return moveItemListAdapter(position, es, inflates);
            case AdapterType.no:
            case AdapterType.select:
            case AdapterType.onClick:
            case AdapterType.onLongClick:
            default:
                return false;
        }
    }


    public final boolean addToAdapter(int position, E e) {
        return addToAdapter(position, e, inflates);
    }

    protected boolean addToAdapter(int position, E e, List<E> inflates) {
        notifyItemInserted(addTo(position, e, inflates));
        return true;
    }

    private int addTo(int position, E e, List<E> inflates) {
        if (position >= inflates.size() || position < 0) {
            position = inflates.size();
            inflates.add(e);
        } else inflates.add(position, e);
        for (int key : other.keySet()) if (key <= position) position++;
        holderList.add(position, e);
        return position;
    }

    public final boolean setToAdapter(int position, E e) {
        return setToAdapter(position, e, inflates);
    }

    protected boolean setToAdapter(int position, E e, List<E> inflates) {
        if (position >= 0 && position < inflates.size()) {
            int p = holderList.indexOf(inflates.get(position));
            inflates.set(position, e);
            holderList.set(p, e);
            notifyItemChanged(p);
            return true;
        }
        return false;
    }

    public final boolean removeToAdapter(int position, E e) {
        return removeToAdapter(position, e, inflates);
    }

    protected boolean removeToAdapter(int position, E e, List<E> inflates) {
        int p = removeTo(position, e, inflates);
        if (p == -1) return false;
        notifyItemRemoved(p);
        return true;
    }

    private int removeTo(int position, E e, List<E> inflates) {
        int p = -1;
        if (e == null) return p;
        if (inflates.contains(e)) {
            p = holderList.indexOf(e);
            position = inflates.indexOf(e);
        } else if (position < 0 || position >= inflates.size()) {
            return p;
        } else {
            p = holderList.indexOf(inflates.get(position));
        }
        inflates.remove(position);
        holderList.remove(p);
        return p;
    }


    protected boolean moveItemToAdapter(int position, E e, List<E> inflates) {
        if (position < 0) return false;
        if (position >= inflates.size())
            position = inflates.size() - 1;
        int f = holderList.indexOf(inflates.get(position));
        int from = inflates.indexOf(e);
        if (from == -1) return false;
        if (f == -1) return addToAdapter(position, e, inflates);
        if (from != position && removeTo(position, e, inflates) != -1) {
            notifyItemMoved(f, addTo(position, e, inflates));
            return true;
        }
        return false;
    }

    public final boolean setListAdapter(int position, List<E> es) {
        return setListAdapter(position, es, es);
    }

    protected boolean setListAdapter(int position, List<E> es, List<E> inflates) {
        if (position >= inflates.size()) return addListAdapter(position, es, inflates);
        else if (position + es.size() >= inflates.size())
            return refreshListAdapter(position, es, inflates);
        else if (position > 0) {
            for (int i = 0; i < es.size(); i++) setToAdapter(position, es.get(i), inflates);
            return true;
        }
        return false;
    }

    public final boolean removeListAdapter(int position, List<E> es) {
        return removeListAdapter(position, es, es);
    }

    protected boolean removeListAdapter(int position, List<E> es, List<E> inflates) {
        int rang = isRang(position, es, holderList);
        if (rang >= 0) {
            inflates.removeAll(es);
            notifyItemRangeRemoved(position, es.size());
            return true;
        } else {
            for (E e : es) removeToAdapter(0, e, inflates);
            return false;
        }
    }


    protected boolean moveItemListAdapter(int position, List<E> es, List<E> inflates) {
        for (int i = 0; i < es.size(); i++)
            moveItemToAdapter(position + i, es.get(i), inflates);
        return isRang(position, es, holderList) >= 0;
    }

    public final boolean addListAdapter(int position, List<E> es) {
        return addListAdapter(position, es, es);
    }

    protected boolean addListAdapter(int position, List<E> es, List<E> inflates) {
        for (E e : es)
            addToAdapter(position,e,inflates);
        return true;
    }

    public final boolean refreshListAdapter(int position, List<E> es) {
        return refreshListAdapter(position, es, es);
    }

    protected boolean refreshListAdapter(int position, List<E> es, List<E> inflates) {
        List<Inflate> l = new ArrayList<>();
        if (position > 0 && position < inflates.size()) {
            List<E> ls = new ArrayList<>();
            ls.addAll(inflates.subList(0,position));
            inflates = ls;
            inflates.addAll(es);
        } else if (inflates.size() == 0) {
            return addListAdapter(position, es, inflates);
        } else {
            inflates.clear();
            inflates.addAll(es);
        }
        int p = -1;
        for (int i = 0; i < other.size() + inflates.size(); i++) {
            if(other.containsKey(i)) l.add(other.get(i));
            else l.add(inflates.get(++p));
        }
        refresh(l, holderList);
        return true;
    }

    @SuppressWarnings("unchecked")
    private void refresh(List<Inflate> es, List<Inflate> holders) {
        holders.clear();
        holders.addAll(es);
        notifyDataSetChanged();
    }

}
