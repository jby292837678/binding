package com.binding.model;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.EnvironmentCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

import com.alibaba.android.arouter.launcher.ARouter;
import com.binding.model.crash.CrashHandler;

import java.io.File;
import java.util.Stack;

import timber.log.Timber;

/**
 * Created by arvin on 2017/11/27.
 */

public class App implements Application.ActivityLifecycleCallbacks {
    public static float popupAlhpa = 1f;
    public static boolean pageWay = false;
    private Stack<Activity> stack = new Stack<>();
    private static final App app = new App();
    public static int vm = BR.vm;
    public static boolean debug = false;

    public static App getInstance() {
        return app;
    }

    public void init(Application application, boolean debug, int vm) {
        App.vm = vm;
        App.debug = debug;
        application.registerActivityLifecycleCallbacks(this);
        if (debug) {
            Timber.plant(new Timber.DebugTree());
            ARouter.openDebug();
            ARouter.openLog();
        } else CrashHandler.getInstance().init(application);
        ARouter.init(application);
    }

    public static Stack<Activity> getStack() {
        return app.stack;
    }

    public static boolean isActivityLive(Class c) {
        for (Activity activity : app.stack) {
            if (activity.getClass().isAssignableFrom(c))
                return true;
        }
        return false;
    }

    public static void finishhWithoutAll(Class<? extends Activity>... cs){
        for (Activity activity : app.stack) {
            boolean a = false;
            for (Class<? extends Activity> c : cs) {
                if(a = c.isAssignableFrom(activity.getClass()))break;
            }
            if (!a) activity.finish();
        }
    }

    public static void finish(Class<? extends Activity>... cs){
        for (Activity activity : app.stack) {
            boolean a = false;
            for (Class<? extends Activity> c : cs) {
                if(a = c.isAssignableFrom(activity.getClass()))break;
            }
            if (!a) activity.finish();
        }
    }

    public void init(Application application) {
        init(application, debug, vm);
    }

    public static Activity getCurrentActivity() {
        return app.stack.lastElement();
    }
    
    public static Activity getIndexActivity(int index) {
        return app.stack.size() >= index ? app.stack.get(app.stack.size() - index) : null;
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

    public static String getString(@StringRes int resId){
        return App.getCurrentActivity().getString(resId);
    }

    public static Drawable getDrawable(@DrawableRes int id) {
        return ContextCompat.getDrawable(getCurrentActivity(), id);
    }

    public static File[] getExternalCacheDirs() {
        return ContextCompat.getExternalCacheDirs(App.getCurrentActivity());
    }

    public static boolean startActivities(@NonNull Intent[] intents) {
        return ContextCompat.startActivities(getCurrentActivity(), intents);
    }

    public static boolean startActivities(@NonNull Intent[] intents, @Nullable Bundle options) {
        return ContextCompat.startActivities(App.getCurrentActivity(), intents, options);
    }

    public static void startActivity(@NonNull Intent intent, @Nullable Bundle options) {
        ContextCompat.startActivity(getCurrentActivity(), intent, options);
    }

    @Nullable
    public static File getDataDir() {
        return ContextCompat.getDataDir(getCurrentActivity());
    }

    @NonNull
    public static File[] getObbDirs() {
        return ContextCompat.getObbDirs(getCurrentActivity());
    }

    @NonNull
    public static File[] getExternalFilesDirs(@Nullable String type) {
        return ContextCompat.getExternalFilesDirs(getCurrentActivity(), type);
    }


    @Nullable
    public static ColorStateList getColorStateList(@ColorRes int id) {
        return ContextCompat.getColorStateList(getCurrentActivity(), id);
    }

    @ColorInt
    public static int getColor(@ColorRes int id) {
        return ContextCompat.getColor(getCurrentActivity(), id);
    }

    public static int checkSelfPermission(@NonNull String permission) {
        return ContextCompat.checkSelfPermission(getCurrentActivity(), permission);
    }

    public static File getNoBackupFilesDir() {
        return ContextCompat.getNoBackupFilesDir(getCurrentActivity());
    }

    public static File getCodeCacheDir() {
        return ContextCompat.getCodeCacheDir(getCurrentActivity());
    }

    @Nullable
    public static Context createDeviceProtectedStorageContext() {
        return ContextCompat.createDeviceProtectedStorageContext(App.getCurrentActivity());
    }

    public static boolean isDeviceProtectedStorage() {
        return ContextCompat.isDeviceProtectedStorage(getCurrentActivity());
    }

    public static void startForegroundService(@NonNull Intent intent) {
        ContextCompat.startForegroundService(getCurrentActivity(), intent);
    }

    public static int getScreenWidth() {
        return getCurrentActivity().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return getCurrentActivity().getResources().getDisplayMetrics().heightPixels;
    }

    public static float dipTopx(float dp) {
        return getCurrentActivity().getResources().getDisplayMetrics().density * dp;
    }

    public static float pxTodip(float px) {
        return px / getCurrentActivity().getResources().getDisplayMetrics().density;
    }

    public static int getWeightWidth(int sum) {
        return getScreenWidth() / sum;
    }

    public static int getWeightHeight(int sum) {
        return getScreenHeight() / sum;
    }
}
