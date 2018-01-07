package com.binding.model;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.binding.model.crash.CrashHandler;

import java.util.Stack;

import timber.log.Timber;

/**
 * Created by arvin on 2017/11/27.
 */

public class App implements Application.ActivityLifecycleCallbacks {
    private Stack<Activity> stack = new Stack<>();
    private static final App app = new App();
    public static int vm = BR.vm;
    public static boolean debug;
    public static App getInstance() {
        return app;
    }

    public void init(Application application,boolean debug,int vm){
        App.vm = vm;
        App.debug = debug;
        application.registerActivityLifecycleCallbacks(this);
        if(debug)Timber.plant(new Timber.DebugTree());
        else CrashHandler.getInstance().init(application);
    }

    public void init(Application application){
        init(application,debug,vm);
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
