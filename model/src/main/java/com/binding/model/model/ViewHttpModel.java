package com.binding.model.model;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;

import com.binding.model.cycle.Container;
import com.binding.model.model.inter.HttpObservable;
import com.binding.model.model.inter.HttpObservableRefresh;
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

public class ViewHttpModel<T extends Container, Binding extends ViewDataBinding, R> extends ViewModel<T, Binding> {
    public final ObservableBoolean loading = new ObservableBoolean(false);
    public final ObservableBoolean enable = new ObservableBoolean(true);
    public final ObservableField<String> error = new ObservableField<>();
    private int pageCount = 16;
    protected int offset = 0;
    private R r;
    private Disposable disposable;
    private HttpObservable<R> rcHttp;

    public static boolean pageWay = false;

    public final void setRcHttp(HttpObservableRefresh<R> rcHttp1){
        setRoHttp((offset1, refresh) -> rcHttp1.http(offset1,(refresh>>1)==1));
    }

    public void setRoHttp(HttpObservable<R> rcHttp) {
        this.rcHttp = rcHttp;
        onHttp(0, 0);
    }

    public void onHttp(int offset, int refresh) {
        this.offset = offset;
        int p = pageWay ? offset / pageCount + 1 : offset;
        if (rcHttp != null)
            rcHttp.http(p, refresh).subscribe(this::accept, this::onThrowable, this::onComplete, this::onSubscribe);
    }

    private void onThrowable(Throwable throwable) {
        loading.set(false);
        error.set(throwable.getMessage());
        BaseUtil.toast(throwable);
    }

    private void onSubscribe(Disposable disposable) {
        loading.set(true);
        this.disposable = disposable;
    }

    public void accept(R r) throws Exception {
        this.r = r;
    }

    private void onComplete() {
        loading.set(false);
    }

    public R getData() {
        return r;
    }

    public void onHttp(int refresh) {
        onHttp(offset, refresh);
    }

    public void onRefresh() {
        onHttp(0);
    }

    public void setEnable(boolean enable) {
        this.enable.set(enable);
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
        if (disposable != null) disposable.dispose();
    }
}
