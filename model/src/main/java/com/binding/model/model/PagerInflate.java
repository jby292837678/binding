package com.binding.model.model;

import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;
import android.support.v4.view.ViewPager;

import com.binding.model.adapter.IModelAdapter;
import com.binding.model.adapter.pager.ViewPagerAdapter;
import com.binding.model.model.inter.Http;
import com.binding.model.model.inter.HttpObservable;
import com.binding.model.model.inter.Inflate;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by arvin on 2018/1/9.
 */

public class PagerInflate<Binding extends ViewDataBinding,E extends Inflate>
        extends ViewInflate<Binding>
        implements ViewPager.OnPageChangeListener{
    public final ObservableInt currentItem = new ObservableInt();
    private final List<E> holderList = new ArrayList<>();
    private final IModelAdapter<E> adapter;
    private HttpObservable<List<E>> rcHttp;
    private boolean pageWay;
    private int pageCount;
    private int offset;
    private Disposable disposable;

    public PagerInflate(IModelAdapter<E> adapter) {
        this.adapter = adapter;
    }


    public void setRoHttp(HttpObservable<List<E>> rcHttp) {
        this.rcHttp = rcHttp;
        onHttp(0, 1);
    }

    private void onHttp(int offset, int refresh) {
        this.offset = offset;
        int p = pageWay ? offset / pageCount + 1 : offset;
        if (rcHttp != null)
            rcHttp.http(p, refresh).subscribe(this::accept, this::onThrowable, this::onComplete, this::onSubscribe);
    }

    private void onSubscribe(Disposable disposable) {
        this.disposable = disposable;
    }

    private void onComplete() {

    }

    private void onThrowable(Throwable throwable) {

    }

    private void accept(List<E> list) {

    }


    public PagerInflate(){
        this(new ViewPagerAdapter<>());
    }

    public IModelAdapter<E> getAdapter() {
        return adapter;
    }

    @Override
    public void onPageSelected(int position) {
        currentItem.set(position%holderList.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
}
