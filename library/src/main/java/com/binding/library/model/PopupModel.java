package com.binding.library.model;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.widget.PopupWindow;

import com.binding.library.cycle.Container;

/**
 * Created by arvin on 2017/11/19.
 */

public class PopupModel<T extends Container, Binding extends ViewDataBinding>  extends ViewModel<T,Binding> {
    private final PopupWindow window = new PopupWindow();
    @Override
    public void attachView(Bundle savedInstanceState, T t) {
        super.attachView(savedInstanceState, t);
        window.setContentView(getDataBinding().getRoot());

    }
}
