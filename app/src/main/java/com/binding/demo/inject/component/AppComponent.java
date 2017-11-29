package com.binding.demo.inject.component;

import android.content.Context;
import android.content.res.Resources;

import com.binding.model.inject.qualifier.context.AppContext;
import com.binding.model.inject.scope.ApplicationScope;
import com.binding.demo.inject.module.AppModule;
import com.binding.demo.inject.module.DataModule;
import com.binding.demo.inject.module.NetWorkModule;

import dagger.Component;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：10:08
 * modify developer：  admin
 * modify time：10:08
 * modify remark：
 *
 * @version 2.0
 */

@ApplicationScope
@Component(modules={AppModule.class, NetWorkModule.class,DataModule.class})
public interface AppComponent {
    @AppContext
    Context context();
    Resources resources();
//    ReadApi getReadApi();
//    @UserSharePreference
//    SharePreferenceUtil NingUtil();
}
