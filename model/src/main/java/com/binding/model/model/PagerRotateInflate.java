package com.binding.model.model;

import android.content.Context;
import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.binding.model.adapter.IModelAdapter;
import com.binding.model.layout.rotate.PagerEntity;
import com.binding.model.layout.rotate.PagerRotateListener;
import com.binding.model.layout.rotate.TimeUtil;
import com.binding.model.model.inter.Inflate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arvin on 2018/1/9.
 */

public class PagerRotateInflate<Binding extends ViewDataBinding,E extends Inflate>
        extends PagerInflate<Binding,E> implements PagerRotateListener<E> {
    private PagerEntity<E> pagerEntity;
    public PagerRotateInflate(IModelAdapter<E> adapter) {
        super(adapter);
    }

    public PagerRotateInflate(){}

    @Override
    public Binding attachView(Context context, ViewGroup co, boolean attachToParent, Binding binding) {
        Binding b = super.attachView(context, co, attachToParent, binding);
        pagerEntity.addRotateListener(this);
        TimeUtil.getInstance().remove(pagerEntity);
        return b;
    }

    @Override
    public void nextRotate(E e) {

    }


}
