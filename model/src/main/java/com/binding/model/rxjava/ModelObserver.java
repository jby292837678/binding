package com.binding.model.rxjava;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;

import com.binding.model.model.ViewModel;
import com.binding.model.util.BaseUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class ModelObserver<T> implements Observer<T> {
    private final Consumer<T> consumer;
    private final LoadingAnimator loadingAnimator;
    private final ViewModel model;

    public ModelObserver(@NonNull ViewModel model, @NonNull Consumer<T> consumer) {
        this.consumer = consumer;
        this.loadingAnimator = this::onLoading;
        this.model = model;
    }

    public ModelObserver(@NonNull ViewModel model, @NonNull Consumer<T> consumer, @NonNull LoadingAnimator loadingAnimator) {
        this.model = model;
        this.consumer = consumer;
        this.loadingAnimator = loadingAnimator;
    }

    public ModelObserver(@NonNull ViewModel model, @NonNull Consumer<T> consumer , @Nullable View animatorView, @NonNull View... clickView) {
        ValueAnimator animator =   ObjectAnimator.ofFloat(animatorView,"Rotation",0,360);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(1000);
        this.model = model;
        this.consumer = consumer;
        this.loadingAnimator = (b,throwable) -> onLoading( animator, b, animatorView,clickView);
    }

    public ModelObserver(@NonNull ViewModel model, @NonNull Consumer<T> consumer, @NonNull Animator animator, @Nullable View animatorView, @NonNull View... clickView) {
        this.model = model;
        this.consumer = consumer;
        this.loadingAnimator = (b,throwable) -> onLoading(animator, b, animatorView,clickView);
    }


    private void onLoading(boolean b, Throwable throwable) {

    }

    private void onLoading( @NonNull Animator animator, boolean b, @Nullable View animatorView,@NonNull View... views) {
        for (View view : views) view.setEnabled(!b);
        if (b) animator.start();
        else animator.end();
        if (animatorView != null) animatorView.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onSubscribe(Disposable d) {
        model.addDisposable(d);
        loadingAnimator.loading(true,null);
    }

    @Override
    public void onNext(T t) {
        try {
            consumer.accept(t);
        } catch (Exception e) {
            BaseUtil.toast(e);
            loadingAnimator.loading(false, e);
        }
    }

    @Override
    public void onError(Throwable e) {
        loadingAnimator.loading(false, e);
        BaseUtil.toast(e);
    }

    @Override
    public void onComplete() {
        loadingAnimator.loading(false, null);
    }

}
