package com.binding.model.adapter;

import java.util.List;

/**
 * Created by pc on 2017/8/26.
 */

public interface IListAdapter<E> {

    /**
     * @param e the data of change
     * @param type 0x00: add the data to the position,
     *             0x01: add to the data to the last
     *             0x10: refresh the data
     *             0x20: remove all the data start with the position
     *             0x30: change the data
     * @return success or failed
     */
    boolean setList(int position, List<E> e, @AdapterHandle int type);

}
