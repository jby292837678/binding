package com.binding.model.adapter.recycler;

import androidx.recyclerview.widget.GridLayoutManager;

import com.binding.model.adapter.IModelAdapter;
import com.binding.model.model.inter.GridInflate;

/**
 * Created by arvin on 2018/1/24.
 */
public class GridSpanSizeLookup<E extends GridInflate> extends GridLayoutManager.SpanSizeLookup {
    private final IModelAdapter<E> adapter;

    public GridSpanSizeLookup(IModelAdapter<E> adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getSpanSize(int position) {
        return adapter.getList().get(position).getSpanSize();
    }

}
