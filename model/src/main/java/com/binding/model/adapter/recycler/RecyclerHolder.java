package com.binding.model.adapter.recycler;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binding.model.R;
import com.binding.model.adapter.IEventAdapter;
import com.binding.model.model.inter.Inflate;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：14:45
 * modify developer：  admin
 * modify time：14:45
 * modify remark：
 *
 * @version 2.0
 */

@SuppressWarnings("unchecked")
public class RecyclerHolder<E extends Inflate> extends RecyclerView.ViewHolder {
    private final ViewGroup container;
    private final ViewDataBinding binding;
    private E e;

    private RecyclerHolder(ViewGroup container,ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        this.container = container;
    }

    RecyclerHolder(ViewGroup container, E e) {
        this(container, e.attachView(container.getContext(),container,false,null));
        this.e = e;
    }

    public synchronized void executePendingBindings(int position,E e, IEventAdapter iEventAdapter) {
        this.e.removeBinding();
        this.e = e;
        this.e.setIEventAdapter(iEventAdapter);
        container.setTag(R.id.holder_position,position);
        this.e.attachView(container.getContext(),container,false,binding);
    }
}
