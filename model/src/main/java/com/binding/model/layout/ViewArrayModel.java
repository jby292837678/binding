package com.binding.model.layout;

import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;

import com.binding.model.adapter.AdapterType;
import com.binding.model.adapter.IEventAdapter;
import com.binding.model.adapter.IModelAdapter;
import com.binding.model.bit.Bit;
import com.binding.model.cycle.Container;
import com.binding.model.model.ViewHttpModel;
import com.binding.model.model.inter.Parse;

import java.util.List;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：9:32
 * modify developer：  admin
 * modify time：9:32
 * modify remark：
 *
 * @version 2.0
 */


public class ViewArrayModel<C extends Container, Binding extends ViewDataBinding, E extends Parse>
        extends ViewHttpModel<C, Binding, List<E>> {
    public ObservableBoolean empty = new ObservableBoolean(true);
    private final IModelAdapter<E> adapter;

    public ViewArrayModel(IModelAdapter<E> adapter) {
        this.adapter = adapter;
    }



    @Override
    public List<E> getData() {
        return adapter.getList();
    }


    /**
     * 0: refresh = true  offset
     * 1: refresh = false offset
     * 2: refresh = true  page
     * 3: refresh = false page
     */
    @Override
    public void accept(List<E> es) throws Exception {
        int position = pageWay ? offset / getPageCount() * getPageCount(): offset;
        adapter.setList(position, es,AdapterType.refresh);
        empty.set(getAdapter().size() == 0);
        error.set("暂无数据");
    }

    public IModelAdapter<E> getAdapter() {
        return adapter;
    }

}
