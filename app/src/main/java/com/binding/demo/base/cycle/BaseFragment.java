package com.binding.demo.base.cycle;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binding.library.cycle.DataBindingFragment;
import com.binding.library.model.ViewModel;
import com.binding.library.util.ReflectUtil;
import com.binding.demo.inject.component.DaggerFragmentComponent;
import com.binding.demo.inject.component.FragmentComponent;
import com.binding.demo.inject.module.FragmentModule;
import com.binding.demo.ui.IkeApplication;

import java.lang.reflect.Method;

import javax.inject.Inject;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：14:00
 * modify developer：  admin
 * modify time：14:00
 * modify remark：
 *
 * @version 2.0
 */


public abstract class BaseFragment<VM extends ViewModel> extends DataBindingFragment<FragmentComponent> {
    @Inject
    public VM vm;
    private FragmentComponent component;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inject(savedInstanceState, container, false);
        initView(rootView);
        return rootView;
    }

    @SuppressWarnings("unchecked")
    public View inject(Bundle savedInstanceState, ViewGroup parent, boolean attachToParent) {
        View view;
        try {
            Method method = FragmentComponent.class.getDeclaredMethod("inject", getClass());
            ReflectUtil.invoke(method, getComponent(), this);
            view = vm.attachView(getContext(), parent, attachToParent,null).getRoot();
            vm.attachView(savedInstanceState,this);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(String.format("name:%1s need to add @Method inject to FragmentComponent", getClass().getSimpleName()));
        }
        return view;
    }


    public void initView(View rootView) {
    }


    @Override
    public Activity getDataActivity() {
        return getActivity();
    }


    @Override
    public FragmentComponent getComponent() {
        if (component == null) {
            component = DaggerFragmentComponent.builder()
                    .appComponent(IkeApplication.getAppComponent())
                    .fragmentModule(new FragmentModule(this))
                    .build();
        }
        return component;
    }

}
