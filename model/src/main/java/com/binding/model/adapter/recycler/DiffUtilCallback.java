package com.binding.model.adapter.recycler;

import android.support.v7.util.DiffUtil;

import com.binding.model.model.inter.Parse;
import com.binding.model.model.inter.Recycler;

import java.util.List;

import static com.binding.model.util.BaseUtil.containsList;

/**
 * Created by apple on 2017/7/28.
 */

public class DiffUtilCallback<E extends Parse> extends DiffUtil.Callback {

    private List<? extends E> oldList;
    private List<? extends E> newList;

    public DiffUtilCallback(List<? extends E> oldList, List<?  extends E> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        if (containsList(oldItemPosition, oldList) && containsList(newItemPosition, newList)) {
            E oldItem = oldList.get(oldItemPosition);
            E newItem = newList.get(newItemPosition);
            if (oldItem instanceof Recycler
                    && newItem instanceof Recycler
                    && oldItem.getClass() == newItem.getClass()) {
                Object old = ((Recycler) oldItem).key();
                Object n = ((Recycler) newItem).key();
                return old!=null&&n!=null&&n.equals(old);
            }
        }
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        if (containsList(oldItemPosition,oldList) && containsList(newItemPosition,newList)){
            E oldItem = oldList.get(oldItemPosition);
            E newItem = newList.get(newItemPosition);
            return oldItem instanceof Recycler
                    && newItem instanceof Recycler
                    && oldItem.getClass() == newItem.getClass()
                    &&((Recycler) oldItem).areContentsTheSame(newItem);
        } else return false;
    }

}