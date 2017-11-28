package com.binding.library.model;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.binding.library.adapter.IEntityAdapter;
import com.binding.library.model.inter.Inflate;


/**
 * Created by apple on 2017/9/8.
 */

public class ViewInflate<Binding extends ViewDataBinding> extends ViewEvent implements Inflate<Binding> {
    private transient Binding dataBinding;
    protected transient IEntityAdapter entityAdapter;

    @Override
    public Binding attachView(Context context, ViewGroup co, boolean attachToParent, Binding binding) {
        return dataBinding =  bind(getLayoutId(),context, co, attachToParent, binding);
    }

    public final <B extends ViewDataBinding>B bind(int layoutId, Context context, ViewGroup co, boolean attachToParent, B binding) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context),layoutId, co, attachToParent);
            binding.setVariable(getVariableName(), this);
        } else {
            binding.setVariable(getVariableName(), this);
            binding.executePendingBindings();
        }
        return binding;
    }

    public final Binding getDataBinding() {
        return dataBinding;
    }

    public final void setEntityAdapter(IEntityAdapter entityAdapter) {
        this.entityAdapter = entityAdapter;
    }

    public void removeBinding() {
        this.dataBinding = null;
        entityAdapter = null;
    }
}
