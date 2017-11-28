package com.binding.library.adapter.recycler;

import android.support.v7.util.DiffUtil;

import com.binding.library.model.inter.Parse;
import com.binding.library.model.inter.ParseRecycler;

import java.util.List;

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
        if(oldItemPosition>=oldList.size()||oldItemPosition>=newList.size()) return false;
        E oldItem = oldList.get(oldItemPosition);
        E newItem = newList.get(oldItemPosition);

        if(oldItem instanceof ParseRecycler&&newItem instanceof ParseRecycler)
            return ((ParseRecycler)oldItem).areItemsTheSame(newItem);
        return false;
//        return oldItem.areItemsTheSame(newList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        if(oldItemPosition>=oldList.size()||oldItemPosition>=newList.size()) return false;
        Parse oldItem = oldList.get(oldItemPosition);
        E newItem = newList.get(oldItemPosition);
        if(oldItem instanceof ParseRecycler&&newItem instanceof ParseRecycler){
            return ((ParseRecycler) oldItem).areContentsTheSame(newItem);
        }else
            return oldItem.equals(newList.get(newItemPosition));
    }
}
