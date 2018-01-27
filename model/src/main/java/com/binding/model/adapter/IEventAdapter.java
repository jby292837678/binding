package com.binding.model.adapter;

import android.view.View;

/**
 * Created by pc on 2017/8/26.
 */

public interface IEventAdapter<E> {
    int NO_POSITION = Integer.MIN_VALUE;
    int ENABLE = 1;
    int CHECK = 2;
    int SELECT = 3;
    boolean setEntity(int position, E e, @AdapterHandle int type, View view);
}
