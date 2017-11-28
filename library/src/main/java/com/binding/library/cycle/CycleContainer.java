package com.binding.library.cycle;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：14:08
 * modify developer：  admin
 * modify time：14:08
 * modify remark：
 *
 * @version 2.0
 */


public interface CycleContainer<T> extends Container{
    T getComponent();
    View inject(Bundle savedInstanceState, ViewGroup parent, boolean attachToParent);
    Lifecycle getLifecycle();
}
