package com.binding.model.adapter.recycler;

import com.binding.model.App;
import com.binding.model.adapter.IModelAdapter;
import com.binding.model.model.inter.GridInflate;

import timber.log.Timber;

/**
 * Created by arvin on 2018/1/24.
 */

public class GridSizeLookup<E extends GridInflate> extends GridSpanSizeLookup<E> {
    private final int size;
    public GridSizeLookup(IModelAdapter<E> adapter, int size) {
        super(adapter);
        this.size = size;
    }

    @Override
    public int getSpanSize(int position) {
        int span = super.getSpanSize(position);
        int count =  size/span;
        if(App.debug&&size%span != 0) Timber.i("span=%1d,size=%2d,",span,size);
        return count == 0?1:count;
    }
}
