package com.binding.model.adapter;

/**
 * Created by pc on 2017/8/26.
 */

public interface IEventAdapter<E> extends IListAdapter<E>{
    boolean setEntity(int position, E e, @AdapterHandle int type);
}
