package com.binding.model.adapter;

/**
 * Created by pc on 2017/8/26.
 */

public interface IEntityAdapter <E>{
    int NO_POSITION = Integer.MAX_VALUE;
    boolean setEntity(int position, E e, @AdapterHandle int type, boolean done);

}
