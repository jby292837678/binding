package com.binding.model.model;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.binding.model.adapter.IEventAdapter;
import com.binding.model.model.inter.Inflate;
import com.binding.model.model.inter.Measure;

import java.util.List;


/**
 * Created by apple on 2017/9/8.
 */

public class ViewInflate<Binding extends ViewDataBinding> extends ViewEvent implements Inflate<Binding> {
    private transient Binding dataBinding;
    private transient IEventAdapter iEventAdapter;

    @Override
    public Binding attachView(Context context, ViewGroup co, boolean attachToParent, Binding binding) {
        registerEvent();
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

    @Override
    public final void setIEventAdapter(IEventAdapter iEventAdapter) {
        this.iEventAdapter = iEventAdapter;
    }

    @Override
    public final IEventAdapter getIEventAdapter() {
        return iEventAdapter;
    }

    @CallSuper
    @Override
    public void removeBinding() {
        this.dataBinding = null;
        unRegisterEvent();
    }


    public void clear(List<? extends Inflate> list) {
        if (list != null)
            for (Inflate inflate : list) {
                inflate.setIEventAdapter(null);
            }
    }
}