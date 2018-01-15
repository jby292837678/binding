package com.binding.model.adapter.recycler;

import android.util.SparseArray;

import com.binding.model.model.ViewInflate;
import com.binding.model.model.inter.Inflate;

import java.util.List;

/**
 * Created by arvin on 2017/12/19.
 */

public class RecyclerInflateAdapter extends RecyclerAdapter<Inflate> {
    private SparseArray<Inflate> array = new SparseArray<>();

    public boolean addInflate(int position,Inflate inflate){
        array.put(position,inflate);
        return addToAdapter(position,inflate);
    }

    public boolean remove(int position){
        Inflate inflate = array.get(position);
        return super.removeToAdapter(NO_POSITION,inflate);
    }

    public void clearArray(){
        for (int i = 0; i < array.size(); i++) {
            removeToAdapter(NO_POSITION,array.valueAt(i));
        }
    }

    /**
     * synchronized the list
     * */
    public final void syncArray(List<Inflate> holderList){
        for (int i = 0; i < array.size(); i++) {
            int key = array.keyAt(i);
            Inflate viewInflate = array.get(key);
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
