package com.binding.model.model;

import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;

import com.binding.model.adapter.AdapterType;
import com.binding.model.adapter.IModelAdapter;
import com.binding.model.cycle.Container;
import com.binding.model.model.inter.HttpObservable;
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


public class ViewArrayModel<C extends Container, Binding extends ViewDataBinding, E extends Parse,Adapter extends IModelAdapter<E>>
        extends ViewHttpModel<C, Binding, List<? extends E>> {
    public ObservableBoolean empty = new ObservableBoolean(true);
    private final Adapter adapter;

    public ViewArrayModel(Adapter adapter) {
        this.adapter = adapter;
    }

    public List<E> getData() {
        return adapter.getList();
    }




//    @Override
//    public void setRoHttp(HttpObservable<List<? super E>> rcHttp) {
//        super.setRoHttp(rcHttp);
//    }

    @Override
    public void accept(List<? extends E> es) throws Exception {
        int position = isPageWay() ? offset / getPageCount() * getPageCount(): offset;
        adapter.setList(position, es,AdapterType.refresh);
    }

    @Override
    public void onComplete() {
        super.onComplete();
        empty.set(getAdapter().size() == 0);
        error.set(empty.get()?"暂无数据":"");
    }

    public Adapter getAdapter() {
        return adapter;
    }

}
