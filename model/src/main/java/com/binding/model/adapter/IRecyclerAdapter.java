package com.binding.model.adapter;

import com.binding.model.model.inter.Inflate;

import java.util.List;

/**
 * Created by arvin on 2018/1/12.
 */

public interface IRecyclerAdapter<E extends Inflate> extends IModelAdapter<E>,IEventAdapter<E> {
    void addEventAdapter(IEventAdapter<?extends E> eventAdapter);
    boolean setListAdapter(int position, List<? extends E> es);
    boolean removeListAdapter(int position, List<? extends E> es);
    boolean addListAdapter(int position, List<? extends E> es);
    boolean refreshListAdapter(int position, List<? extends E> es);
    boolean moveListAdapter(int position, List<? extends E> es);

    boolean setToAdapter(int position, E e);
    boolean addToAdapter(int position, E e);
    boolean removeToAdapter(int position, E e);
    boolean moveToAdapter(int position, E e);
}

