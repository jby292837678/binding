package com.binding.model.adapter;

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


public interface IModelAdapter<E> extends IEventAdapter<E>, IListAdapter<E> {
    List<E> getList();
    int size();
    void setIEventAdapter(IEventAdapter<E> iEntityAdapter);

}
