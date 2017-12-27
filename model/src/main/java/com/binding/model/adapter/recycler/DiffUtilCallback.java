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

    private List<E> oldList;
    private List<E> newList;

    public DiffUtilCallback(List<E> oldList, List<E> newList) {
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
            E newItem = newList.get(oldItemPosition);
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
        if (oldItemPosition >= oldList.size() || oldItemPosition >= newList.size()) return false;
        E oldItem = oldList.get(oldItemPosition);
        E newItem = newList.get(oldItemPosition);
        if (oldItem instanceof Recycler
                && newItem instanceof Recycler
                && oldItem.getClass() == newItem.getClass()) {
            return ((Recycler) oldItem).areContentsTheSame(newItem);
        }
        return false;
    }

}