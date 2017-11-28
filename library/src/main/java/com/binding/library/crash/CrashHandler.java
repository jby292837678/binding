package com.binding.library.crash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;

import com.binding.library.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import timber.log.Timber;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 *
 * @author user
 */
@SuppressLint("SdCardPath")
public class CrashHandler implements UncaughtExceptionHandler {
    private static CrashHandler INSTANCE = new CrashHandler();
    private Context mContext;
    private UncaughtExceptionHandler mDefaultHandler;
    private Map<String, String> infos = new HashMap<>();
    private String srcDirPath;

    /**
     * 保证只有一个 CrashHandler 实例
     */
    private CrashHandler() {}

    /**
     * 获取 CrashHandler 实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        // initMap();
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        this.srcDirPath = Environment.getExternalStorageDirectory() +File.separator + context.getPackageName().substring(srcDirPath.lastIndexOf("\\.")+1);
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        Timber.d("TEST Crash:init");
    }

    public void setSrcDirPath(String srcDirPath) {
        this.srcDirPath = Environment.getExternalStorageDirectory() +File.separator + srcDirPath;
    }

    /**
     * 当 UncaughtException 发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Timber.e(e);
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理，收集错误信息，发送错误报告等操作均在此完成
     *
     * @param ex
     * @return true：如果处理了该异常信息；否则返回 false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        ex.printStackTrace();
        saveCrashInfoInFile(ex);
        new Thread(() -> {
            Looper.prepare();
//				Util.makeToast(mContext, error);
            Looper.loop();
        }).start();
        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public String collectDeviceInfo(Context ctx, boolean flag) {
        String string_buf;
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);

            if (pi != null) {
                String versionName = pi.versionName == null ? "null"
                        : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            Timber.e("an error occured when collect package info %1s", e.getMessage());
            infos.put("versionName", "unknow");
            infos.put("versionCode", "unknow");
        }

        string_buf = "versionName:" + infos.get("versionName")
                + ", versionCode:" + infos.get("versionCode") + '\n';

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Timber.e(field.getName() + " : " + field.get(null));
                if (flag)
                    string_buf += field.getName() + ": "
                            + infos.get(field.getName()) + '\n';
            } catch (Exception e) {
                Timber.e("an error occured when collect crash info", e);
            }
        }
        return string_buf;
    }

    /**
     * 保存错误信息到文件中 *
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfoInFile(Throwable ex) {
//		StringBuffer sb = getTraceInfo(ex);
        StringBuffer sb = new StringBuffer();
//        Util.makeToast(mContext, sb.toString());
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            SimpleDateFormat timeName = new SimpleDateFormat("MM_dd_HH_mm_ss_SSS", Locale.CHINA);
            String fileName =String.format(Locale.getDefault(),"crash_%1s_error.log",timeName.format(new Date()));
            SimpleDateFormat dateDirs = new SimpleDateFormat("yyyyMM/dd", Locale.CHINA);
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = String.format(Locale.getDefault(),"%1s/crash/%2s",srcDirPath,dateDirs.format(new Date()));
                if(!TextUtils.isEmpty(FileUtil.createPathDir(srcDirPath,path))){
                    FileOutputStream fos = new FileOutputStream(path +File.separatorChar+ fileName);
                    fos.write(sb.toString().getBytes());
                    fos.close();
                }
            }
            return fileName;
        } catch (Exception e) {
            Timber.e("an error occured while writing file...", e);
        }
        return null;
    }

    /**
     * 整理异常信息
     *
     * @param e
     * @return
     */
    public StringBuffer getTraceInfo(Throwable e) {
        StringBuffer sb = new StringBuffer();
        Throwable ex = e.getCause() == null ? e : e.getCause();
        StackTraceElement[] stacks = ex.getStackTrace();
        sb.append(collectDeviceInfo(mContext, false));
        for (int i = 0; i < stacks.length; i++) {
            if (i == 0) {
//                setError(ex.toString());
            }
            sb.append(ex.toString())
                    .append("\n");
        }
        Timber.e(sb.toString());
        return sb;
    }

}