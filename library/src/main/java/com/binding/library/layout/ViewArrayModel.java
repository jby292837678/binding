package com.binding.library.layout;

import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;

import com.binding.library.adapter.AdapterType;
import com.binding.library.adapter.IEntityAdapter;
import com.binding.library.adapter.IModelAdapter;
import com.binding.library.cycle.Container;
import com.binding.library.model.ViewHttpModel;
import com.binding.library.model.inter.Parse;

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
    public ObservableBoolean refreshing = new ObservableBoolean(true);
    public ObservableBoolean emptyVisibility = new ObservableBoolean(false);
    private final IModelAdapter<E> adapter;

    public ViewArrayModel(IModelAdapter<E> adapter) {
        this.adapter = adapter;
    }

    @Override
    public List<E> getData() {
        return adapter.getList();
    }

    public void setRefreshing(boolean refreshing) {
        this.refreshing.set(refreshing);
    }



    @Override
    public void accept(List<E> es) throws Exception {
        adapter.setList(getAdapter().size(), es, getPage() > 1 ? AdapterType.add : AdapterType.refresh);
        emptyVisibility.set(getAdapter().size() != 0);
        loading.set(false);
    }

    public void setEnable(boolean aBoolean) {}

    public IModelAdapter<E> getAdapter() {
        return adapter;
    }


    public void setIEntityAdapter(IEntityAdapter<E> IEntityAdapter) {
        adapter.setIEntityAdapter(IEntityAdapter);
    }
}
