package com.binding.model.model;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.binding.model.adapter.IEntityAdapter;
import com.binding.model.model.inter.Inflate;


/**
 * Created by apple on 2017/9/8.
 */

public class ViewInflate<Binding extends ViewDataBinding> extends ViewEvent implements Inflate<Binding> {
    private transient Binding dataBinding;
    private transient IEntityAdapter entityAdapter;
//    private transient View.OnClickListener onClickListener;
//    private transient View.OnLongClickListener onLongClickListener;
//    private transient View.OnTouchListener onTouchListener;

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
//        onClickListener = null;
//        onLongClickListener = null;
//        onTouchListener = null;
    }

//    public void onClick(View view){
//        if(onClickListener!=null)onClickListener.onClick(view);
//    }
//
//    public boolean onLongClick(View view){
//        return onLongClickListener!=null&&onLongClickListener.onLongClick(view);
//    }
//
//    public boolean onTouch(View view, MotionEvent event){
//        return onTouchListener!=null&&onTouchListener.onTouch(view,event);
//    }
}
