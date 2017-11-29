package com.binding.model.adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.binding.model.adapter.IEntityAdapter;
import com.binding.model.model.ViewInflate;

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
public class RecyclerHolder<E extends ViewInflate> extends RecyclerView.ViewHolder {

    private final ViewGroup container;
    private  E e;

    private RecyclerHolder(ViewGroup container, View itemView) {
        super(itemView);
        this.container = container;
    }

    RecyclerHolder(ViewGroup container, E e) {
        this(container,e.attachView(container.getContext(),container,false,null).getRoot());
        this.e = e;
    }

    public static RecyclerView.ViewHolder createRecyclerHolder(ViewGroup container,View view) {
        return new RecyclerHolder<>(container,view);
    }

    public void executePendingBindings(E e, IEntityAdapter iEntityAdapter) {
        this.e.removeBinding();
        this.e = e;
        this.e.setEntityAdapter(iEntityAdapter);
        this.e.attachView(container.getContext(),container,false,e.getDataBinding());
    }
}
