package com.binding.model.model;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.binding.model.App;
import com.binding.model.adapter.IModelAdapter;
import com.binding.model.adapter.recycler.RecyclerAdapter;
import com.binding.model.adapter.recycler.RecyclerSelectAdapter;
import com.binding.model.cycle.Container;
import com.binding.model.layout.recycler.RecyclerModel;

import io.reactivex.functions.Consumer;

/**
 * Created by arvin on 2017/11/19.
 */

public class PopupRecyclerModel<T extends Container, Binding extends ViewDataBinding, E extends ViewInflateRecycler> extends RecyclerModel<T, Binding, E> {
    public PopupRecyclerModel(){
        super(new RecyclerAdapter<>());
    }

    public PopupRecyclerModel(IModelAdapter<E> adapter) {
        super(adapter);
    }

    private final PopupWindow window = new PopupWindow();

    @Override
    public Binding attachView(Context context, ViewGroup co, boolean attachToParent, Binding binding) {
        Binding viewBinding = super.attachView(context, co, attachToParent, binding);
        window.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setFocusable(true);
        window.setContentView(viewBinding.getRoot());
        window.setBackgroundDrawable(new BitmapDrawable());
        window.setOutsideTouchable(true);
        window.setTouchable(true);
        return viewBinding;
    }

    public PopupWindow getWindow() {
        return window;
    }

    public void showInit(Consumer<PopupWindow> consumer) {
        if (window.isShowing()) {
            window.dismiss();
        } else {
            try {
                if (getAdapter() instanceof RecyclerSelectAdapter)
                    ((RecyclerSelectAdapter) getAdapter()).checkAll(false);
                getDataBinding().setVariable(App.vm, this);
                consumer.accept(window);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public void show(Consumer<PopupWindow> consumer) {
        if (window.isShowing()) {
            window.dismiss();
        } else
            try {
                consumer.accept(window);
            } catch (Throwable e) {
                e.printStackTrace();
            }
    }

}
