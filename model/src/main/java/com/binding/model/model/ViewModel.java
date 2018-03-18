package com.binding.model.model;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.binding.model.Config;
import com.binding.model.cycle.Container;
import com.binding.model.cycle.CycleContainer;
import com.binding.model.model.inter.Model;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.ListCompositeDisposable;
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
        implements Model<T, Binding> {
    private transient WeakReference<T> weakReference;
    private String path;
    private Bundle bundle;
    private final ListCompositeDisposable disposables = new ListCompositeDisposable();

    public final Binding attachContainer(T t, ViewGroup co, boolean attachToParent, Bundle savedInstanceState) {
        Binding binding = attachView(t.getDataActivity(), co, attachToParent, null);
        attachView(savedInstanceState, t);
        return binding;
    }

    @CallSuper
    @Override
    public void attachView(Bundle savedInstanceState, T t) {
        if (getModelView().model()) eventModel.add(this);
        weakReference = new WeakReference<>(t);
        if (t instanceof CycleContainer) ((CycleContainer) t).getLifecycle().addObserver(this);
        path = t.getDataActivity().getIntent().getStringExtra(Config.path);
        bundle = t.getDataActivity().getIntent().getBundleExtra(Config.bundle);
    }

    public void addLifeCycleObserver(LifecycleObserver observer) {
        if (getT()!=null&&getT() instanceof CycleContainer) ((CycleContainer) getT()).getLifecycle().addObserver(observer);
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
        disposables.dispose();
        if (getT() != null) {
            unRegisterEvent();
            weakReference.clear();
            removeBinding();
        }
    }

    public void finish() {
        if (getT() != null && getT().getDataActivity() != null) getT().getDataActivity().finish();
    }

    @Override
    public void onModelEvent(String tag, Object... objects) {

    }

    public void onRightClick(View view) {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


    public final void addDisposable(Disposable disposable){
        disposables.add(disposable);
    }


    public void goPath(){
        if(!TextUtils.isEmpty(path)){
            ARouter.getInstance().build(path).with(bundle).navigation();
        }
    }

    public void startActivity(Class<? extends Activity> c,Bundle bundle) {
        if (getT() == null) return;
        Intent intent = new Intent(getT().getDataActivity(),c);
        intent.putExtras(bundle);
        getT().getDataActivity().startActivity(intent);
    }


    public void startActivity(Class<? extends Activity> c) {
        if (getT() == null) return;
        Intent intent = new Intent(getT().getDataActivity(),c);
        getT().getDataActivity().startActivity(intent);
    }

    public void startActivity(Intent intent) {
        if (getT() == null) return;
        getT().getDataActivity().startActivity(intent);
    }


}
