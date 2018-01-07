package com.binding.model.model;

import android.support.annotation.NonNull;

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
    private final transient ModelView modelView;
    private transient int modelIndex = 0;
    public ViewParse(@NonNull ModelView modelView) {
        this.modelView = modelView;
    }

    public ViewParse(){
        this.modelView = BaseUtil.findModelView(getClass());
        if(modelView == null)throw new RuntimeException("should to add @ModelView to the class:" + getClass());
    }

    @Override
    public final int getLayoutId(){
        return getLayoutId(getModelIndex());
    }

    public final int getLayoutId(int viewType){
        return modelView.value()[viewType];
    }

    @Override
    public final ModelView getModelView() {
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
        return modelView.name().length==0? App.vm:modelView.name()[getModelIndex()];
    }
}