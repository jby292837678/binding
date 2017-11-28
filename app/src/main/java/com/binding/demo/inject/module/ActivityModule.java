package com.binding.demo.inject.module;


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;

import com.binding.library.inject.qualifier.context.ActivityContext;
import com.binding.library.inject.qualifier.manager.ActivityFragmentManager;
import com.binding.library.inject.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：15:17
 * modify developer：  admin
 * modify time：15:17
 * modify remark：
 *
 * @version 2.0
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @ActivityContext
    @Provides
    @ActivityScope
    Context provideActivityContext() {
        return activity;
    }

    @Provides
    @ActivityScope
    @ActivityFragmentManager
    FragmentManager provideFragmentManager() {
        if (activity instanceof FragmentActivity)
            return ((FragmentActivity) activity).getSupportFragmentManager();
        return null;
    }

    @Provides
    @ActivityScope
    DisplayMetrics provideDisplayMetrics() {
        return activity.getResources().getDisplayMetrics();
    }

    @Provides
    @ActivityScope
    LayoutInflater provideLayoutInflater() {
        return LayoutInflater.from(activity);
    }
}
