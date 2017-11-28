package com.binding.library;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.binding.library.crash.CrashHandler;

import java.util.Stack;

import timber.log.Timber;

/**
 * Created by arvin on 2017/11/27.
 */

public class App implements Application.ActivityLifecycleCallbacks {
    private Stack<Activity> stack = new Stack<>();
    private static final App app = new App();

    public static App getInstance() {
        return app;
    }

    public void init(Application application){
        application.registerActivityLifecycleCallbacks(this);
        ARouter.init(application);
        Timber.plant(new Timber.DebugTree());
        if (BuildConfig.DEBUG) {
            ARouter.openDebug();
            ARouter.openLog();
        }else{
            CrashHandler.getInstance().init(application);
        }
        ARouter.init(application);
    }

    public static Activity getCurrentActivity(){
        return app.stack.lastElement();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        stack.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        stack.remove(activity);
    }
}
