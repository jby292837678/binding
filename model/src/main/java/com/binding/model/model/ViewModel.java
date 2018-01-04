package com.binding.model.model;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.view.View;

//import com.binding.library.BR;
import com.android.databinding.library.baseAdapters.BR;
import com.binding.model.App;
import com.binding.model.cycle.Container;
import com.binding.model.cycle.CycleContainer;
import com.binding.model.model.inter.Model;

import java.lang.ref.WeakReference;

import timber.log.Timber;



/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：15:58
 * modify developer：  admin
 * modify time：15:58
 * modify remark：
 * it's use to bind activity fragment or layout
 *
 * @version 2.0
 */

public class ViewModel<T extends Container, Binding extends ViewDataBinding> extends ViewInflate<Binding>
        implements Model<T, Binding>, LifecycleObserver {
    private transient WeakReference<T> weakReference;

    @CallSuper
    @Override
    public void attachView(Bundle savedInstanceState, T t) {
        if (getModelView().model()) eventModel.add(this);
        weakReference = new WeakReference<>(t);
        if (t instanceof CycleContainer) ((CycleContainer) t).getLifecycle().addObserver(this);
    }

    public void addLifeCycleObserver(T t, LifecycleObserver observer) {
        if (t instanceof CycleContainer) ((CycleContainer) t).getLifecycle().addObserver(observer);
    }

    public void addLifeCycleObserver(LifecycleObserver observer) {
        addLifeCycleObserver(getT(), observer);
    }

    private int getBindName() {
        int modelIndex = getModelIndex();
        int[] bindName = getModelView().name();
        int length = bindName.length;
        return modelIndex < length ? bindName[modelIndex] : App.vm;
    }



    public static void dispatchModel(String tag, Object... objects) {
        for (ViewModel model : eventModel) model.onModelEvent(tag, objects);
    }

    private @LayoutRes int getLayoutRes() {
        int modelIndex = getModelIndex();
        int[] layout = getModelView().value();
        int length = layout.length;
        return layout[modelIndex < length ? modelIndex : 0];
    }

    @Override
    public T getT() {
        T t = null;
        if (weakReference != null) t = weakReference.get();
        if (t == null)
            Timber.e(weakReference == null ? "weakReference ==null" : "cycleContainer object == null");
        return t;
    }

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        if (getModelView().model()) eventModel.remove(this);
        if (getT() != null) {
            unRegisterEvent();
            weakReference.clear();
        }
    }

    public void finish() {
        if (getT() != null && getT().getDataActivity() != null) getT().getDataActivity().finish();
    }

    public void onModelEvent( String tag, Object... objects){

    }

    public void onRightClick(View view) {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
