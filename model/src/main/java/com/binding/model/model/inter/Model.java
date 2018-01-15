package com.binding.model.model.inter;

import android.arch.lifecycle.LifecycleObserver;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.binding.model.cycle.Container;
import com.binding.model.model.ViewModel;

import java.util.HashSet;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：14:05
 * modify developer：  admin
 * modify time：14:05
 * modify remark：
 *
 * @version 2.0
 */


public interface Model<T extends Container,Binding extends ViewDataBinding> extends Inflate<Binding>,LifecycleObserver {
    HashSet<ViewModel> eventModel = new HashSet<>();
    void attachView(Bundle savedInstanceState, T t);
    T getT();
}