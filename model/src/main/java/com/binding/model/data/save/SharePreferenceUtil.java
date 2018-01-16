package com.binding.model.data.save;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.binding.model.Config;
import com.binding.model.util.ReflectUtil;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import timber.log.Timber;

/**
 * project：cutv_ningbo
 * description：
 * create developer： Arvin
 * create time：2015/12/15 10:44.
 * modify developer：  Arvin
 * modify time：2015/12/15 10:44.
 * modify remark：
 *
 * @version 2.0
 */
public class SharePreferenceUtil {
    private static SharePreferenceUtil userShare;
    private SharedPreferences share;
    private SharedPreferences.Editor editor;

    private SharePreferenceUtil(Context context, String name) {
        Context application = context instanceof Application ? context : context.getApplicationContext();
        share = application.getSharedPreferences(name, Activity.MODE_PRIVATE);
        editor = share.edit();
        editor.apply();
    }

    public static SharePreferenceUtil getUserInstance(Context context) {
        SharePreferenceUtil util = userShare;
        if (util == null) {
            synchronized (SharePreferenceUtil.class) {
                util = userShare;
                if (util == null) {
                    util = new SharePreferenceUtil(context, Config.user);
                    userShare = util;
                }
            }
        }
        return util;
    }

    private void clear() {
        editor.clear();
        editor.commit();
    }

    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    @SuppressWarnings("unchecked")
    private void putValue(String key, Object value,boolean remove) {
        if (value == null) return;
        if (value instanceof String) {
            editor.putString(key, value.toString());
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Set) {
            editor.putStringSet(key, (Set<String>) value);
        } else {
            Class clazz = value.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                Object object = ReflectUtil.beanGetValue(f, value);
                if (!ReflectUtil.isFieldNull(object)) putValue(f.getName(), object,remove);
                else if(remove) editor.remove(f.getName());
            }
            Timber.w("key:%1s,value:%2s", key, value);
        }
    }

    /**
     * put the information to the memory,please put the value class is instanceof String,boolean,float,long,int and Set<String>
     * can'model put the other type to the memory,otherwise,the method will not set the value to the memory,and if the value is
     * instanceof Set<?> ,but It isn'model instanceof Set<String>,this will throw activity ClassCastException;
     *
     * @param key   key
     * @param value value
     */
    public void setValue(String key, @NonNull Object value) {
        putValue(key, value,false);
        editor.commit();
    }

    public <T> void setAllDto(T t) {
        Class clazz = t.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            Object object = ReflectUtil.beanGetValue(f, t);
            if (!ReflectUtil.isFieldNull(object)) putValue(f.getName(), object,true);
            else editor.remove(f.getName());
        }
        editor.commit();
    }

    public <T> void setNotNullDto(T t) {
        Class clazz = t.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            Object object = ReflectUtil.beanGetValue(f, t);
            if (!ReflectUtil.isFieldNull(object))
                putValue(f.getName(), object,false);
        }
        editor.commit();
    }


    public Map<String, ?> getAll() {
        return share.getAll();
    }


    public <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("please check the construct of the " + clazz.getName() +
                    ", \tthe class of construct must be have activity no parameter ", e);
        }
    }

    public <T> T getAllDto(Class<T> c, SharedPreferences share) {
        T t = newInstance(c);
        return getAllDto(t, share);
    }

    public <T> T getAllDto(T t, SharedPreferences share) {
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field f : fields) {
            Object value = getValue(f.getName(), f.getType(), share);
            if (!ReflectUtil.isFieldNull(value))
                ReflectUtil.beanSetValue(f, t, value);
        }
        return t;
    }

    public <T> T getAllDto(Class<T> clazz) {
        return getAllDto(clazz, share);
    }

    public <E> E getValue(String key, Class<E> clazz) {
        return getValue(key, clazz, share);
    }

    /**
     * 遍历Share中的key值，让其与实体类属性名匹配,匹配成功则取出值
     */
    @SuppressWarnings("unchecked")
    private <E> E getValue(String key, Class<E> clazz, SharedPreferences share) {
        if (clazz == String.class) {
            return (E) share.getString(key, "");
        } else if (clazz == int.class || clazz == Integer.class) {
            Integer result = share.getInt(key, 0);
            return (E) result;
        } else if (clazz == long.class || clazz == Long.class) {
            Long result = share.getLong(key, 0);
            return (E) result;
        } else if (clazz == boolean.class || clazz == Boolean.class) {
            Boolean result = share.getBoolean(key, false);
            return (E) result;
        } else if (clazz == float.class || clazz == Float.class) {
            Float result = share.getFloat(key, 0);
            return (E) result;
        } else if (clazz == Set.class) {
            Set<String> result = share.getStringSet(key, null);
            return (E) result;
        }
        return null;
    }

    public String getString(String key) {
        String value = share.getString(key, "");
        Timber.i("key:%1s,value:%2s", key, value);
        return value;

    }

    public int getInt(String key) {
        int value = share.getInt(key, 0);
        Timber.i("key:%1s,value:%2d", key, value);
        return value;
    }

    public int getInt(String key, int i) {
        return share.getInt(key, i);
    }

    public boolean getBoolean(String first_open, boolean b) {
        return share.getBoolean(first_open, b);
    }
}
