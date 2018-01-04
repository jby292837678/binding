package com.binding.model.model;

import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;

import com.binding.model.cycle.Container;
import com.binding.model.model.inter.HttpObservable;
import com.binding.model.util.BaseUtil;

import io.reactivex.disposables.Disposable;

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

public class ViewHttpModel<T extends Container,Binding extends ViewDataBinding,R> extends ViewModel<T,Binding> {

    public final ObservableBoolean loading = new ObservableBoolean(false);
    private HttpObservable<R> rcHttp;
    private boolean enable = true;
    private int pageCount = 16;
    protected int offset = 0;
    private R r;
    private Disposable disposable;

    /**
     *  0: refresh = false  offset
     *  1: refresh = true   offset
     *  2: refresh = false  page
     *  3: refresh = true   page
     * */
    protected final boolean pageWay;

    public ViewHttpModel() {
        this(false);
    }

    public ViewHttpModel(boolean pageWay) {
        this.pageWay = pageWay;
    }



    public void setRcHttp(HttpObservable<R> rcHttp) {
        this.rcHttp = rcHttp;
        onHttp();
    }

    public void onHttp(int offset, boolean refresh){
        this.offset = offset;
        loading.set(true);
        int p = pageWay?offset/pageCount+1:offset;
       if(rcHttp!=null){
           disposable  = rcHttp.http(p,refresh)
               .subscribe(this::accept,this::onThrowable);
       }
    }

    public void onThrowable(Throwable throwable){
        loading.set(false);
        BaseUtil.toast(throwable);
    }

    public void accept(R r) throws Exception {
        this.r = r;
        loading.set(false);
    }

    public R getData(){
        return r;
    }

    public void onHttp(boolean refresh) {
        onHttp(offset, refresh);
    }

    public void onHttp() {
        onHttp(true);
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean getEnable(){
        return enable;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(disposable!=null)disposable.dispose();
    }

}
