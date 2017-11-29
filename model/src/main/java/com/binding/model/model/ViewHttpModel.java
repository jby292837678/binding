package com.binding.model.model;

import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.PropertyChangeRegistry;
import android.databinding.ViewDataBinding;

import com.binding.model.cycle.Container;
import com.binding.model.model.inter.Http;

import io.reactivex.functions.Consumer;

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

public class ViewHttpModel<T extends Container,Binding extends ViewDataBinding,R> extends ViewModel<T,Binding> implements Consumer<R>
    ,Observable {
    public final ObservableBoolean loading = new ObservableBoolean(false);
    private R r;
    private Http<R> rcHttp;
    private boolean enable = true;
    private int pageCount = 15;
//    protected int offset = 0;
    private int page = 1;
    private transient PropertyChangeRegistry mCallbacks;

    public void setRcHttp(Http<R> rcHttp) {
        this.rcHttp = rcHttp;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPage() {
        return page;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void onHttp(int page, boolean refresh){
        this.page = page;
        loading.set(true);
       if(rcHttp!=null) rcHttp.http(page,refresh)
               .subscribe(this
               ,this::onThrowable);
    }

    public void onThrowable(Throwable throwable){
        loading.set(false);
    }

    @Override
    public void accept(R r) throws Exception {
        this.r = r;
        loading.set(false);
    }

    public R getData(){
        return r;
    }

    public void onHttp(boolean refresh) {
        onHttp(page,refresh);
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean getEnable(){
        return enable;
    }


    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        synchronized (this) {if (mCallbacks == null) mCallbacks = new PropertyChangeRegistry();}
        mCallbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        synchronized (this) {if (mCallbacks == null) return;}
        mCallbacks.remove(callback);
    }

    public void notifyChange() {
        synchronized (this) {if (mCallbacks == null) return;}
        mCallbacks.notifyCallbacks(this, 0, null);
    }

    public void notifyPropertyChanged(int fieldId) {
        synchronized (this) {if (mCallbacks == null) return;}
        mCallbacks.notifyCallbacks(this, fieldId, null);
    }
}
