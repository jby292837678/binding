package com.binding.model.adapter.recycler;

import android.util.SparseArray;

import com.binding.model.model.ViewInflate;

import java.util.List;

/**
 * Created by arvin on 2017/12/19.
 */

public class RecyclerInflateAdapter extends RecyclerAdapter<ViewInflate> {
    private SparseArray<ViewInflate> array = new SparseArray<>();

    public boolean addInflate(int position,ViewInflate inflate){
        array.put(position,inflate);
        return addToAdapter(position,inflate);
    }

    /**
     * synchronized the list
     * */
    public final void syncArray(List<ViewInflate> holderList){
        for (int i = 0; i < array.size(); i++) {
            int key = array.keyAt(i);
            ViewInflate viewInflate = array.get(key);
            int actual = holderList.indexOf(viewInflate);
            if(actual == key)continue;
            boolean success = actual == -1? super.addToAdapter(key,viewInflate,holderList): super.moveToAdapter(actual,viewInflate,holderList);
        }
    }

    @Override
    public int size() {
        return super.size()-array.size();
    }
}
