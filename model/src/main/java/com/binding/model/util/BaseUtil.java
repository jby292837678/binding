package com.binding.model.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

//import com.alibaba.android.arouter.facade.Postcard;
//import com.alibaba.android.arouter.launcher.ARouter;
import com.binding.model.App;
import com.binding.model.R;
import com.binding.model.adapter.AdapterType;
import com.binding.model.data.encrypt.Params;
import com.binding.model.model.ModelView;
import com.binding.model.layout.rotate.ObtainCodeEntity;
import com.binding.model.layout.rotate.TimeUtil;
import com.binding.model.model.ViewModel;
import com.binding.model.data.exception.ApiException;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.functions.Consumer;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：14:10
 * modify developer：  admin
 * modify time：14:10
 * modify remark：
 *
 * @version 2.0
 */


public class BaseUtil {

    public static Class getSuperGenericType(Class clazz) {
        return getSuperGenericType(clazz, 0);
    }

    public static Class getSuperGenericType(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) return Object.class;
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) index = params.length - 1;
        if (!(params[index] instanceof Class) || index == -1) return Object.class;
        return (Class) params[index];
    }

    public static ModelView findModelView(Class<?> thisCls) {
        if (thisCls == null) return null;
        ModelView contentView = thisCls.getAnnotation(ModelView.class);
        if (contentView == null) return findModelView(thisCls.getSuperclass());
        return contentView;
    }

    public static String findQuery(Field field) {
        Params key = field.getAnnotation(Params.class);
        return key==null?field.getName():key.value();
    }

    public static ViewGroup.LayoutParams params(View view, boolean parent) {
        ViewGroup.LayoutParams params;
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        if (view != null) {
            if (!parent) view = (View) view.getParent();
            if (view == null) {
                params = new ViewGroup.LayoutParams(width, height);
            } else if (view instanceof RadioGroup) {
                params = new RadioGroup.LayoutParams(width, height);
            } else if (view instanceof LinearLayout) {
                params = new LinearLayout.LayoutParams(width, height);
            } else if (view instanceof RelativeLayout) {
                params = new RelativeLayout.LayoutParams(width, height);
            } else if (view instanceof ViewPager) {
                params = new ViewPager.LayoutParams(view.getContext(), null);
            } else if (view instanceof DrawerLayout) {
                params = new DrawerLayout.LayoutParams(width, height);
            } else if (view instanceof Toolbar) {
                if (Build.VERSION.SDK_INT > 20) {
                    params = new Toolbar.LayoutParams(width, height);
                } else params = new ViewGroup.LayoutParams(width, height);
            } else if (view instanceof RecyclerView) {
                params = new RecyclerView.LayoutParams(width, height);
            } else if (view instanceof SwipeRefreshLayout) {
                params = new SwipeRefreshLayout.LayoutParams(width, height);
            } else if (view instanceof AbsListView) {
                params = new AbsListView.LayoutParams(width, height);
            } else {
                params = new ViewGroup.LayoutParams(width, height);
            }
        } else {
            params = new ViewGroup.LayoutParams(width, height);
        }
        params.width = width;
        params.height = height;
        return params;
    }


    /**
     * @param clazz the current class
     * @param c     the class of the interface
     * @return the generic types of interface
     * for example :
     * interface I<T>{}
     * interface I1<T>{}
     * class Parent implements I<String>{}
     * class C extends Parent implements I1<Integer>{}
     * AdapterType[] type = getInterfacesGenericTypes(C.class,I.class)
     * type = {String.class}
     */
    public static Type[] getInterfacesGenericTypes(Class clazz, Class<?> c) {
        if (clazz == null) return new Type[0];
        Class<?>[] cls = clazz.getInterfaces();
        for (int index = 0; index < cls.length; index++) {
            if (c.isAssignableFrom(cls[index])) {
                Type genType = clazz.getGenericInterfaces()[index];
                return genType instanceof ParameterizedType ? ((ParameterizedType) genType).getActualTypeArguments() : new Type[0];
            }
        }
        return getInterfacesGenericTypes(clazz.getSuperclass(), c);
    }

    /**
     * @param clazz the current class
     * @param c     the class of the interface
     * @return the generic types of interface
     * for example :
     * interface I<T>{}
     * interface I1<T>{}
     * class Parent implements I<String>{}
     * class C extends Parent implements I1<Integer>{}
     * AdapterType[] type = getInterfacesGenericTypes(C.class,I.class)
     * type = {String.class}
     */
    public static Class getInterfacesGenericType(Class clazz, Class<?> c) {
        return (Class) getInterfacesGenericTypes(clazz, c)[0];
    }

    public static int getLayoutId(int layoutIndex, Class clazz) {
        ModelView modelView = BaseUtil.findModelView(clazz);
        if (modelView == null)
            throw new RuntimeException(clazz != null ?
                    "this class :" + clazz.getName() + " need to add @ModelView"
                    : "the clazz == null,please use the method setAdapter(Class clazz) before setAdapter");
        int index = layoutIndex;
        if (layoutIndex >= modelView.value().length) index = 0;
        return modelView.value()[index];
    }

    public static <T> T newInstance(Class<T> t, Object... args) {
        T adapter = null;
        if (t != null) {
            if (args.length == 0) {
                try {
                    adapter = t.newInstance();
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Class[] clazs = new Class[args.length];
                    for (int i = 0; i < args.length; i++)
                        clazs[i] = args[i].getClass();
                    Constructor<T> con = t.getConstructor(clazs);
                    con.newInstance(args);
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        return adapter;
    }

//    public static boolean isValidToast(TextView view, String error) {
//        setError(view, error);
//        return error == null;
//    }


    public static void setError(View view, String errorText) {
        if (!TextUtils.isEmpty(errorText)) {
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.RED);
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(errorText);
            ssbuilder.setSpan(fgcspan, 0, errorText.length(), 0);
//            view.setError(ssbuilder);
        }
    }

    public static boolean isValidToast(View view, String error) {
        setError(view, error);
        if (!TextUtils.isEmpty(error))
            Toast.makeText(view.getContext(), error, Toast.LENGTH_SHORT).show();
        return TextUtils.isEmpty(error);
    }

    public static String getConfirmError(String password, String confirm) {
        if (TextUtils.isEmpty(confirm)) return "确认密码不能为空";
        if (!password.equals(confirm)) return "两次输入的密码不一致";
        return null;
    }

    public static String getPhoneError(String mobiles) {
        if (TextUtils.isEmpty(mobiles)) return "手机号不能为空";
        Pattern p = Pattern.compile("^((13[0-9])|(17[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        boolean valid = m.matches();
        return valid ? null : "不合法的手机号";
    }

    public static String getIdentityError(String identity_num) {
        if (TextUtils.isEmpty(identity_num)) return "身份证号码不能为空";
        Pattern p = Pattern.compile("^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$");
        Matcher m = p.matcher(identity_num);
        boolean valid = m.matches();
        return valid ? null : "身份证号码不合法";
    }

    public static String getRealNameError(String realName) {
        if (TextUtils.isEmpty(realName)) return "姓名不能为空";
        return null;
    }

    public static String getPayPasswordError(String password) {
        if (TextUtils.isEmpty(password)) return "密码不能为空";
        if (password.length() != 6) return "密码长度位6位";
        if (!TextUtils.isDigitsOnly(password)) return "密码必须为纯数字";
        return null;
    }

    public static String getPasswordError(String password) {
        if (TextUtils.isEmpty(password)) return "密码不能为空";
        if (password.length() < 6) return "密码长度不小于6位";
        if (password.length() > 16) return "密码长度最长为16位";
//        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
//        boolean result = Pattern.compile(regex).matcher(password).find();
//        if (!result) return "密码需要字母和数字";
        return null;
    }

    /**
     *
     *  public String getPhoneError(String mobiles) {
     if (TextUtils.isEmpty(mobiles)) return "手机号不能为空";
     Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
     Matcher m = p.matcher(mobiles);
     System.out.println(m.matches() + "---");
     boolean valid = m.matches();
     return valid ? null : "不合法的手机号";
     }

     public String getPasswordError(String password) {
     if (TextUtils.isEmpty(password)) return "密码不能为空";
     if (password.length() < 6) return "密码长度不小于6位";
     return null;
     }

     public String getCodeError(String code) {
     if (TextUtils.isEmpty(code)) return "验证码不能为空";
     if (code.length() < 5) return "验证码不小于5位";
     return null;
     }


     public boolean isValidToast(TextView view, String error) {
     view.setError(error);
     return error == null;
     }*/

    public static String getCodeError(String code) {
        if (TextUtils.isEmpty(code)) return "验证码不能为空";
        if (code.length() < 4) return "验证码不小于4位";
        return null;
    }
    public static String getNickNameError(String nickname) {
        if (TextUtils.isEmpty(nickname)) return "昵称不能为空";
        if (nickname.length() > 10) return "昵称不能大于10位";
        return null;
    }
    public static DisplayMetrics getDisplayMetrics(Context context) {
//        DisplayMetrics dm = new DisplayMetrics();
//        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return context.getResources().getDisplayMetrics();
    }

    public static double dipToPx(Context context, double dip) {
        return context.getResources().getDisplayMetrics().density * dip + 0.5;
    }

    public static double pxToDip(Context context, double px) {
        return px / context.getResources().getDisplayMetrics().density + 0.5;
    }


    public static boolean isLive(String currentUrl) {
        return currentUrl != null
                && (currentUrl.startsWith("rtmp://")
                || (currentUrl.startsWith("http://") && currentUrl.endsWith(".m3u8"))
                || (currentUrl.startsWith("http://") && currentUrl.endsWith(".flv")));
    }


    public static boolean checkPermission(Activity activity, String... permissions) {
        if (activity == null) return false;
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }
    public static boolean checkPermission(ViewModel model, String... permissions) {
        if (model.getT() == null) return false;
        return checkPermission(model.getT().getDataActivity(),permissions);
    }

    public static void checkPermission(Activity activity, Consumer<Boolean> onNext, String... permissions) {
        if (BaseUtil.checkPermission(activity, permissions)) {
            accept(onNext, true);
        } else {
            new RxPermissions(activity)
                    .request(permissions)
                    .subscribe(onNext, Throwable::printStackTrace);
        }
    }

    public static void checkPermission(ViewModel model, Consumer<Boolean> onNext, String... permissions) {
        if (BaseUtil.checkPermission(model, permissions)) {
            accept(onNext, true);
        } else {
            new RxPermissions(model.getT().getDataActivity())
                    .request(permissions)
                    .subscribe(onNext, Throwable::printStackTrace);
        }
    }

    public static boolean canReadFile(File file, Activity activity) {
        return activity != null && checkPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) && file.exists();
    }

    public static <T> void accept(Consumer<T> onNext, T t) {
        try {
            if (onNext != null) onNext.accept(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean checkNull(Object checkable) {
        return checkable != null;
    }

    public static void toast(Context context, Throwable e) {
        ApiException exception= cause(e);
        if (exception != null) toast(context, exception.getMessage());
        if(App.debug)e.printStackTrace();
    }

    private static ApiException cause(Throwable throwable){
        if(throwable instanceof ApiException)return (ApiException) throwable;
        else {
            if(throwable.getCause() == null)return null;
            return cause(throwable.getCause());
        }
    }

    public static void toast(Fragment fragment, Throwable e) {
        toast(fragment.getActivity(), e);
    }

    public static void toast(ViewModel model, String text) {
        if (model == null || model.getT() == null) return;
        toast(model.getT().getDataActivity(), text);
    }

    public static boolean toast(View view, String message) {
        return toast(view.getContext(), message);
    }

    public static void sendSMSFailed(View view, String e) {
        view.setEnabled(true);
        toast(view.getContext(), e);
    }

    public static void sendSMSFailed(View view, Throwable e) {
        view.setEnabled(true);
        toast(view.getContext(), e);
    }

    public static ObtainCodeEntity sendSMSSuccess(TextView view) {
        BaseUtil.toast(view, "短信发送成功");
        view.setEnabled(false);
        ObtainCodeEntity entity = new ObtainCodeEntity();
        entity.setTime(60);
        entity.setPagerRotateListener(integer -> {
            if (integer == 0) {
                view.setEnabled(true);
                view.setText(R.string.obtain);
            } else {
                view.setText(String.format(Locale.getDefault(), "%1ds", integer));
            }
        });
        TimeUtil.getInstance().add(entity);
        return entity;
    }

    public static void toast(View view, Throwable e) {
        if (view instanceof SwipeRefreshLayout) ((SwipeRefreshLayout) view).setRefreshing(false);
        toast(view.getContext(), e);
    }

    public static boolean toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        return true;
    }

    public static void snack(View view, Throwable e, View.OnClickListener action, String actionText) {
        if (e instanceof ApiException) {
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_SHORT)
                    .setActionTextColor(ContextCompat.getColor(view.getContext(), android.R.color.white))
                    .setAction(actionText, action)
                    .show();
        }
//        else if(e instanceof Exception && !BuildConfig.DEBUG)
//            PgyCrashManager.reportCaughtException(App.getCurrentActivity(), (Exception)e);
    }

    public static void close(Closeable stream) {
        try {
            if (stream != null) stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static long obtainTime(String time, String FORMAT_DATE_TIME) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_DATE_TIME, Locale.CHINA);
            Date date = formatter.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.getTimeInMillis();
        } catch (ParseException e) {
            return 0;
        }
    }

    public static String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public static void appInstall(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(context, "com.binding.library.fileProvider", file);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }


    public static String obtainTime(long time, String FORMAT_DATE_TIME) {
        Date d = new Date(time * 1000);
        SimpleDateFormat sf = new SimpleDateFormat(FORMAT_DATE_TIME, Locale.CHINA);
        return sf.format(d);
    }

    public static int getTimeState(String startTime, String endTime, String FORMAT_DATE_TIME) {
        long nowTime = System.currentTimeMillis();
        if (nowTime > obtainTime(endTime, FORMAT_DATE_TIME)) return 1;
        else if (nowTime > obtainTime(startTime, FORMAT_DATE_TIME)) return 0;
        else return -1;
    }

    public static <F> boolean setEntity(List<F> list, int position, F e, int type) {
        switch (type) {
            case AdapterType.add:
                list.add(position, e);
                break;
            case AdapterType.refresh:
                break;
            case AdapterType.remove:
                list.remove(position);
                break;
            case AdapterType.set:
                list.set(position, e);
                break;
            case AdapterType.no:
            default:
                return false;
        }
        return true;
    }

    public static <F> boolean setList(List<F> list, int position, List<F> es, int type) {
        switch (type) {
            case AdapterType.add:
                list.addAll(position, es);
                break;
            case AdapterType.refresh:
                list.clear();
                list.addAll(es);
                break;
            case AdapterType.remove:
                list.removeAll(es);
                break;
            case AdapterType.set:
                for (F f : es) {
                    if (position < es.size())
                        list.set(position, f);
                    else
                        list.add(position, f);
                    position++;
                }
            case AdapterType.no:
            default:
                return false;
        }
        return true;
    }

    public static CharSequence colorText(Text... texts) {
        StringBuilder builder = new StringBuilder();
        for (Text text : texts) builder.append(text);
//        Timber.i("html:%1s",builder.toString());
        return Html.fromHtml(builder.toString());
    }

    public static Text T(String text, boolean line, int color, int big) {
        return new Text(text, line, color, big);
    }

    public static Text T(String text, boolean line, String color, int big) {
        return new Text(text, line, color, big);
    }

    public static Text T(String text, boolean line, int color) {
        return new Text(text, line, color, 0);
    }

    public static Text T(String text, boolean line, String color) {
        return new Text(text, line, color, 0);
    }

    public static Text T(String text, int color) {
        return new Text(text, false, color);
    }

    public static Text T(String text, boolean line) {
        return new Text(text, line, 0);
    }

    public static Text T(String text) {
        return new Text(text, 0);
    }

    public static void toast(Throwable throwable) {
        toast(App.getCurrentActivity(),throwable);
    }

    public static void toast(ViewModel model, Throwable throwable) {
        if(model.getT() !=null){
            toast(model.getT().getDataActivity(),throwable);
        }
    }

    public static void toast(String text){
        toast(App.getCurrentActivity(),text);
    }


    public static boolean isNetworkConnected(Context context) {
        if (context == null) return false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }


    public static boolean contain(int value, int min, int max) {
        return value > min && value < max;
    }

    public static boolean containsList(int value, List list) {
        return contain(value, -1, list.size());
    }

}
