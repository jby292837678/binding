package com.binding.model.model;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.support.annotation.CallSuper;
import android.text.TextUtils;

import com.binding.model.App;
import com.binding.model.cycle.Container;
import com.binding.model.cycle.MainLooper;
import com.binding.model.model.inter.HttpObservable;
import com.binding.model.model.inter.HttpObservableRefresh;
import com.binding.model.model.request.RecyclerRefresh;
import com.binding.model.model.request.RecyclerStatus;
import com.binding.model.util.BaseUtil;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static com.binding.model.model.request.RecyclerStatus.init;

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

public abstract class ViewHttpModel<T extends Container, Binding extends ViewDataBinding, R> extends ViewModel<T, Binding>
        implements Observer<R>{


    public final ObservableBoolean loading = new ObservableBoolean(false);
    public final ObservableBoolean enable = new ObservableBoolean(true);
    public final ObservableField<String> error = new ObservableField<>();
    private int pageCount = 16;
    protected int offset = 0;
    protected int headIndex = 0;


    public void setHeadIndex(int headIndex) {
        this.headIndex = headIndex;
    }


    public final void onHttp(int offset,@RecyclerRefresh int refresh) {
        if(refresh>0)offset = headIndex;
        this.offset = offset;
        int o = offset>headIndex?offset-headIndex:0;
        int p = pageWay ? o/ pageCount + 1 : o;
        if(rcHttp!=null)http(rcHttp,p,refresh);
    }

    protected void http(HttpObservable<? extends R> rcHttp, int p, int refresh) {
        rcHttp.http(p, refresh)
                .flatMap(BaseUtil::from)
                .subscribe(this);
    }

    @CallSuper
    public void onSubscribe(Disposable disposable) {
        loading.set(true);
        addDisposable(disposable);
    }


    @Override
    public void onError(Throwable e) {
        loading.set(false);
        String msg = e.getMessage();
        if(TextUtils.isEmpty(msg))msg = "获取数据失败";
        error.set(msg);
        BaseUtil.toast(e);
    }

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume(){
        if(!loading.get()&& !TextUtils.isEmpty(error.get())){
            onHttp(headIndex, RecyclerStatus.resumeError);
        }
    }

    @CallSuper
    public void onComplete() {
        loading.set(false);
        error.set("");
    }


    public void onHttp(@RecyclerRefresh int refresh) {
        onHttp(offset, refresh);
    }

    public void onRefresh() {
        onHttp(RecyclerStatus.loadTop);
    }

    public void setEnable(boolean enable) {
        this.enable.set(enable);
    }

    public void setPageWay(boolean pageWay) {
        this.pageWay = pageWay;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageCount() {
        return pageCount;
    }


    private HttpObservable<? extends R> rcHttp;
    private boolean pageWay = App.pageWay;


    public final void setRcHttp(HttpObservableRefresh<? extends R> rcHttp1){
        setRoHttp((offset1, refresh) ->  rcHttp1.http(offset1,refresh>0));
    }

    public void setRoHttp(HttpObservable<? extends R> rcHttp) {
        this.rcHttp = rcHttp;
        onHttp(headIndex, init);
    }

    public boolean isPageWay() {
        return pageWay;
    }
}
