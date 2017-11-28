package com.binding.library.adapter;

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


public interface IModelAdapter<E> extends IEventAdapter<E> {
//    void clear();
    List<E> getList();
    int size();
    void setIEntityAdapter(IEntityAdapter<E> iEntityAdapter);
//    void checkPosition(E i,E last);
    boolean setEntity(int position, E e, @AdapterHandle int type);
}
