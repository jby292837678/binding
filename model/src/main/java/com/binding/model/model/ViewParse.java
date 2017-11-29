package com.binding.model.model;

import com.android.databinding.library.baseAdapters.BR;
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

    @Override
    public int getLayoutId(){
        return getLayoutId(getModelIndex());
    }

    public int getLayoutId(int viewType){
        return getModelView().value()[viewType];
    }

    @Override
    public ModelView getModelView() {
        if (modelView == null) {
            modelView = BaseUtil.findModelView(getClass());
            if (modelView == null) throw new RuntimeException("should to add @ModelView to the class:" + getClass());
        }
        return modelView;
    }

    @Override
    public int getModelIndex() {
        return 0;
    }

    public int getVariableName() {
        return getModelView().name().length==0? BR.vm:getModelView().name()[getModelIndex()];
    }
}