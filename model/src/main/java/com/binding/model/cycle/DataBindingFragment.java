package com.binding.model.cycle;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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


public abstract class DataBindingFragment<C> extends Fragment implements CycleContainer<C> {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inject(savedInstanceState, container, false);
        initView(rootView);
        return rootView;
    }

    @SuppressWarnings("unchecked")
    public abstract View inject(Bundle savedInstanceState, ViewGroup parent, boolean attachToParent);


    public void initView(View rootView) {}


    @Override
    public Activity getDataActivity() {
        return getActivity();
    }



}
