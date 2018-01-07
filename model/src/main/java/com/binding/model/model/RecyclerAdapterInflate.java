package com.binding.model.model;

import android.databinding.ViewDataBinding;

import com.binding.model.adapter.recycler.RecyclerAdapter;
import com.binding.model.model.inter.Inflate;

/**
 * Created by arvin on 2018/1/7.
 */

public class RecyclerAdapterInflate<Binding extends ViewDataBinding,E extends Inflate> extends RecyclerInflate<Binding,E,RecyclerAdapter<E>> {
    public RecyclerAdapterInflate( boolean page) {
        super(new RecyclerAdapter<>(), page);
    }

    public RecyclerAdapterInflate() {
        this(false);
    }
}
