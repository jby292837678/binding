package com.binding.model.adapter;

import android.view.View;

import java.util.List;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：14:30
 * modify developer：  admin
 * modify time：14:30
 * modify remark：
 *
 * @version 2.0
 */


public interface IModelAdapter<E> extends IListAdapter<E> {
    int size();
    void clear();
    List<E> getList();
    boolean setIEntity(int position, E e, @AdapterHandle int type, View view);
//    void addEventAdapter(IEventAdapter<E> eventAdapter);


}
