package com.binding.demo.ui;

import android.support.multidex.MultiDexApplication;

import com.binding.model.App;
import com.binding.demo.inject.component.AppComponent;
import com.binding.demo.inject.component.DaggerAppComponent;
import com.binding.demo.inject.module.AppModule;
import com.binding.demo.ui.user.User;

/**
 * Created by apple on 2017/6/23.
 */

public class IkeApplication extends MultiDexApplication {
    private static IkeApplication application;
    private static AppComponent appComponent;
//    private String version;

    @Override
    public void onCreate() {
        super.onCreate();
        App.getInstance().init(this);
        User user = new User(this);
//        try {
//            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
        application = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public static IkeApplication getApp() {
        return application;
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

//    public String getVersion() {
//        return version;
//    }



}
