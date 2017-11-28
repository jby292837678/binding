package com.binding.library.model.inter;

import android.view.ViewGroup;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：11:38
 * modify developer：  admin
 * modify time：11:38
 * modify remark：
 *
 * @version 2.0
 */


public interface Item<T> {
    T getItem(int position, ViewGroup container);
}
