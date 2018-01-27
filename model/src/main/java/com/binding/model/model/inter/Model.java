package com.binding.model.model.inter;

import android.arch.lifecycle.LifecycleObserver;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.binding.model.cycle.Container;
import com.binding.model.model.ViewModel;
import com.binding.model.util.ReflectUtil;

import java.lang.reflect.Method;
import java.util.HashSet;

import timber.log.Timber;

import static com.binding.model.util.ReflectUtil.arrayToString;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：14:05
 * modify developer：  admin
 * modify time：14:05
 * modify remark：
 *
 * @version 2.0
 */


public interface Model<T extends Container,Binding extends ViewDataBinding> extends Inflate<Binding>,LifecycleObserver {
    HashSet<Model> eventModel = new HashSet<>();
    void attachView(Bundle savedInstanceState, T t);
    T getT();
    void onModelEvent(String tag, Object[] objects);
    static void dispatchModel(String tag, Object... args) {
        for (Model model : eventModel){
            ReflectUtil.invoke(tag,model,args);
            Timber.e("method:%1s \tobject:%2s \t params: %2s", tag, model.getClass().getName(),arrayToString(args));
            model.onModelEvent(tag, args);
        }
    }
}