package com.binding.model.adapter.list;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.binding.model.R;
import com.binding.model.adapter.IEventAdapter;
import com.binding.model.adapter.IRecyclerAdapter;
import com.binding.model.model.inter.Inflate;

import java.util.ArrayList;
import java.util.List;

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


public class ListAdapter<E extends Inflate> extends BaseAdapter implements IRecyclerAdapter<E>, IEventAdapter<E> {
    private final List<E> list = new ArrayList<>();
    private IEventAdapter<E> iEventAdapter = this;
    private int count = 0;

    @Override
    public int getCount() {
        return count == 0 || count > list.size() ? list.size() : count;
    }

    @Override
    public E getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        Inflate inflate = list.get(position);
        ViewDataBinding binding;
        if (convertView == null) {
            binding = inflate.attachView(context, parent, false, null);
        } else {
            Inflate out = (Inflate) convertView.getTag(R.id.holder_inflate);
            binding = inflate.attachView(context, parent, false,
                    out.getLayoutId() == inflate.getLayoutId() ? out.getDataBinding():null);
            out.removeBinding();
        }
        convertView = binding.getRoot();
        convertView.setTag(R.id.holder_inflate, inflate);
        inflate.setIEventAdapter(iEventAdapter);
        return convertView;
    }


    @Override
    public boolean setList(int position, List<E> e, int type) {
        return false;
    }


    @Override
    public boolean setEntity(int position, E e, int type, View view) {
        return false;
    }

    @Override
    public List<E> getList() {
        return list;
    }

    @Override
    public int size() {
        return list.size();
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void setIEventAdapter(IEventAdapter<E> iEventAdapter) {
        this.iEventAdapter = iEventAdapter;
    }

}
