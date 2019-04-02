package com.binding.model.model;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.binding.model.App;
import com.binding.model.model.inter.Parse;
import com.binding.model.util.BaseUtil;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：8:58
 * modify developer：  admin
 * modify time：8:58
 * modify remark：
 *
 * @version 2.0
 */


public class ViewParse implements Parse {
    private transient ModelView modelView;
    private transient int modelIndex = 0;

    public ViewParse(){
        this.modelView = BaseUtil.findModelView(getClass());
    }

    @Override
    public final int getLayoutId(){
        return getLayoutId(getModelIndex());
    }

    public final @LayoutRes int getLayoutId(int viewType){
        int[] layout = getModelView().value();
        int length = layout.length;
        return layout[viewType < length ?viewType : 0];
    }

    @Override
    public final ModelView getModelView() {
        if(modelView == null){
            modelView = BaseUtil.findModelView(getClass());
            if(modelView == null) throw new RuntimeException("should to add @ModelView to the class:" + getClass());
        }
        return modelView;
    }

    @Override
    public final void setModelIndex(int modelIndex) {
        this.modelIndex =modelIndex;
    }

    @Override
    public int getModelIndex() {
        return modelIndex;
    }

    public final int getVariableName() {
        int[] bindName = getModelView().name();
        int length = bindName.length;
        return getModelIndex() < length ? bindName[getModelIndex()] : App.vm;
    }
}