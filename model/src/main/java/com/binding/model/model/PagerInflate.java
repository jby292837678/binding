package com.binding.model.model;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.ViewPager;
import android.view.View;

import com.binding.model.adapter.AdapterType;
import com.binding.model.adapter.IEventAdapter;
import com.binding.model.adapter.IModelAdapter;
import com.binding.model.adapter.pager.ViewPagerAdapter;
import com.binding.model.layout.rotate.PagerEntity;
import com.binding.model.layout.rotate.PagerRotateListener;
import com.binding.model.layout.rotate.TimeEntity;
import com.binding.model.layout.rotate.TimeUtil;
import com.binding.model.model.inter.Http;
import com.binding.model.model.inter.HttpObservable;
import com.binding.model.model.inter.HttpObservableRefresh;
import com.binding.model.model.inter.Inflate;
import com.binding.model.util.BaseUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by arvin on 2018/1/9.
 */

public class PagerInflate<Binding extends ViewDataBinding,E extends Inflate>
        extends ViewInflate<Binding>
        implements ViewPager.OnPageChangeListener,PagerRotateListener<E> {
    public final ObservableInt currentItem = new ObservableInt();
    private final List<E> holderList = new ArrayList<>();
    private final IModelAdapter<E> adapter;
    private HttpObservable<List<E>> rcHttp;
//    private boolean pageWay;
//    private int pageCount;
    private int offset;
    private Disposable disposable;
    public final ObservableBoolean loading = new ObservableBoolean(false);
    private PagerEntity<E> pagerEntity;
    public PagerInflate(IModelAdapter<E> adapter) {
        this.adapter = adapter;
    }


    public final void setRcHttp(HttpObservableRefresh<List<E>> rcHttp1){
        setRoHttp((offset1, refresh) -> rcHttp1.http(offset1,refresh>0));
    }

    public void setRoHttp(HttpObservable<List<E>> rcHttp) {
        this.rcHttp = rcHttp;
        onHttp(0, 1);
    }

    private void onHttp(int offset, int refresh) {
        this.offset = offset;
//        int p = pageWay ? offset / pageCount + 1 : offset;
        if (!loading.get()&&rcHttp != null){
            loading.set(true);
            rcHttp.http(0, refresh).subscribe(this::accept, this::onThrowable, this::onComplete, this::onSubscribe);
        }
    }

    private void onSubscribe(Disposable disposable) {
        this.disposable = disposable;
    }

    private void onComplete() {
        loading.set(false);
    }

    private void onThrowable(Throwable throwable) {
        BaseUtil.toast(throwable);
        loading.set(false);
    }

    private void accept(List<E> list) {
        getAdapter().setList(IEventAdapter.NO_POSITION,list, AdapterType.refresh);
        pagerEntity = new PagerEntity<E>(list,this);
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
        if(pagerEntity!=null)TimeUtil.getInstance().switching(pagerEntity,state);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    public void onErrorClick(View view){
        onHttp(offset,2);
    }

    @Override
    public void nextRotate(E e) {
        currentItem.set(holderList.indexOf(e));
    }

    @Override
    public void removeBinding() {
        super.removeBinding();
        if(disposable!=null&&!disposable.isDisposed())disposable.dispose();
        if(pagerEntity!=null)TimeUtil.getInstance().remove(pagerEntity);
    }
}
