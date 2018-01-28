package com.binding.model.adapter.list;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.binding.model.R;
import com.binding.model.adapter.AdapterType;
import com.binding.model.adapter.IEventAdapter;
import com.binding.model.adapter.IModelAdapter;
import com.binding.model.model.inter.Inflate;

import java.util.ArrayList;
import java.util.List;

import static com.binding.model.util.BaseUtil.containsList;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：10:58
 * modify developer：  admin
 * modify time：10:58
 * modify remark：
 *
 * @version 2.0
 */

public class ListAdapter<E extends Inflate> extends BaseAdapter implements IModelAdapter<E>, IEventAdapter<E> {
    private final List<E> holderList = new ArrayList<>();
    private final IEventAdapter<E> iEventAdapter = this;
    private final List<IEventAdapter<E>> eventAdapters = new ArrayList<>();
    private int count = 0;

    @Override
    public int getCount() {
        return count == 0 || count > holderList.size() ? holderList.size() : count;
    }

    @Override
    public E getItem(int position) {
        return holderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        Inflate inflate = holderList.get(position);
        ViewDataBinding binding;
        if (convertView == null) {
            binding = inflate.attachView(context, parent, false, null);
        } else {
            Inflate out = (Inflate) convertView.getTag(R.id.holder_inflate);
            binding = inflate.attachView(context, parent, false,
                    out.getLayoutId() == inflate.getLayoutId() ? out.getDataBinding() : null);
            out.removeBinding();
        }
        convertView = binding.getRoot();
        convertView.setTag(R.id.holder_inflate, inflate);
        inflate.setIEventAdapter(iEventAdapter);
        return convertView;
    }

    @Override
    public boolean setEntity(int position, E e, int type, View view) {
        for (IEventAdapter<E> eventAdapter : eventAdapters) {
            if (eventAdapter instanceof IModelAdapter)
                return ((IModelAdapter) eventAdapter).setIEntity(position, e, type, view);
            else if (eventAdapter.setEntity(position, e, type, view)) return true;
        }
        return false;
    }

    @Override
    public void addEventAdapter(IEventAdapter<E> eventAdapter) {
        eventAdapters.add(0, eventAdapter);
    }

    @Override
    public boolean setIEntity(int position, E e, int type, View view) {
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
    public boolean setList(int position, List<E> es, int type) {
        switch (type) {
            case AdapterType.add:
                return addListAdapter(position, es);
            case AdapterType.refresh:
                return refreshListAdapter(position, es);
            case AdapterType.remove:
            case AdapterType.set:
            case AdapterType.move:
            case AdapterType.select:
            case AdapterType.no:
            case AdapterType.onClick:
            case AdapterType.onLongClick:
            default:
                return false;
        }
    }


    public boolean addToAdapter(int position, E e) {
        if (containsList(position, holderList))
            holderList.add(position, e);
        else holderList.add(e);
        notifyDataSetChanged();
        return true;
    }

    public boolean removeToAdapter(int position, E e) {
        if (holderList.contains(e)) position = holderList.indexOf(e);
        if (containsList(position, holderList)) holderList.remove(e);
        notifyDataSetChanged();
        return containsList(position, holderList);
    }


    public boolean setToAdapter(int position, E e) {
        if (containsList(position, holderList)) {
            holderList.set(position, e);
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    public boolean moveToAdapter(int position, E e) {
        if (position >= holderList.size()) position = holderList.size() - 1;
        if (containsList(position, holderList)) {
            int from = holderList.indexOf(e);
            if (from != position && holderList.remove(e)) holderList.add(position, e);
            notifyDataSetChanged();
            return true;
        }
        return false;
    }


    public boolean addListAdapter(int position, List<E> es) {
        if (containsList(position, holderList))
            holderList.addAll(position, es);
        else holderList.addAll(es);
        notifyDataSetChanged();
        return containsList(position, es);
    }

    public boolean refreshListAdapter(int position, List<E> es) {
        List<E> l;
        if (containsList(position, holderList)) {
            l = holderList.subList(0, position);
        } else if (holderList.size() != position || holderList.size() == 0) {
            return addListAdapter(position, es);
        } else l = es;
        holderList.clear();
        holderList.addAll(l);
        notifyDataSetChanged();
        return true;
    }


    @Override
    public List<E> getList() {
        return holderList;
    }

    @Override
    public int size() {
        return holderList.size();
    }

    public void setCount(int count) {
        this.count = count;
    }


}
