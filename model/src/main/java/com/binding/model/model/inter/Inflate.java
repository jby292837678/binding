package com.binding.model.model.inter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.ViewGroup;

import com.binding.model.adapter.IEventAdapter;

/**
 * Created by apple on 2017/9/8.
 */

public interface Inflate<Binding extends ViewDataBinding> extends Parse{
    Binding attachView(Context context, ViewGroup co, boolean attachToParent, Binding set);
    void removeBinding();
    void setIEventAdapter(IEventAdapter iEventAdapter);
    ViewDataBinding getDataBinding();
}