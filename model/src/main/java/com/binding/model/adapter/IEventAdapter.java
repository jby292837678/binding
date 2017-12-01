package com.binding.model.adapter;

import android.view.View;

/**
 * Created by pc on 2017/8/26.
 */

public interface IEventAdapter<E> extends IListAdapter<E>{
    boolean setEntity(int position, E e, @AdapterHandle int type, View view);
}
