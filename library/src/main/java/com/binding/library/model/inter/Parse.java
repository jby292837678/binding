package com.binding.library.model.inter;

import com.binding.library.model.ModelView;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：10:22
 * modify developer：  admin
 * modify time：10:22
 * modify remark：
 *
 * @version 2.0
 */


public interface Parse extends Entity {
    ModelView getModelView();
    int getModelIndex();
    int getLayoutId();

}
