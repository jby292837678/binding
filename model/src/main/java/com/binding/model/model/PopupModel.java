package com.binding.model.model;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.binding.model.cycle.Container;

/**
 * Created by arvin on 2017/11/19.
 */

public class PopupModel<T extends Container, Binding extends ViewDataBinding>  extends ViewModel<T,Binding> {
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
}
