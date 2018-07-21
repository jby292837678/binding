package com.binding.model.data.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.binding.model.util.ReflectUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

import io.reactivex.functions.Consumer;
import timber.log.Timber;

import static com.binding.model.util.ReflectUtil.arrayToString;
import static com.binding.model.util.ReflectUtil.invoke;

@SuppressWarnings("unchecked")
@SuppressLint("DefaultLocale")
public class JsonDeepUtil {
    private static JsonDeepUtil jsonDeepUtil = new JsonDeepUtil();
    private Consumer<Object> consumer;

    public static <T> T parse(String json, Class<T> c) {
        return jsonDeepUtil.jsonParse(json, c);
    }

    public <T> T[] parses(String json, Class<T> c) {
        return jsonDeepUtil.jsonParses(json, c);
    }

    public <T> T parse(String json,Type type){
        return jsonDeepUtil.jsonParse(json,type);
    }

    public void setConsumer(Consumer<Object> consumer) {
        this.consumer = consumer;
    }



    public <T> T jsonParse(String json,Type type){
        try {
            return jsonParse(new JSONObject(json),type);
        } catch (JSONException e) {
            Timber.w("please use json jsonParse data");
        }
        return null;
    }

    public <T> T jsonParse(JSONObject json,Type type){
        if(type instanceof Class){
            return jsonParse(json,(Class<T>)type);
        }else if(type instanceof ParameterizedType){
            Class<T> c = (Class<T>)((ParameterizedType) type).getRawType();
            T t = ReflectUtil.newInstance(c);
            for (Field field : c.getDeclaredFields()) {
                Type fieldType = field.getType();
                Timber.i(fieldType.getTypeName());
            }
//            for (Type type1 : ((ParameterizedType) type).getActualTypeArguments()) {
//
//            }
            return t;
        }
        return null;
    }

    public <T> T jsonParse(String json, Class<T> c) {
        if (TextUtils.isEmpty(json)) return null;
        try {
            if (json.trim().charAt(0) == '{') {
                return accept(jsonParse(new JSONObject(json), c));
            } else if (json.trim().charAt(0) == '[') {
                Timber.w("please use method jsonParses(json,type)");
            }
        } catch (JSONException e) {
            Timber.w("please use json jsonParse data");
        }
        return null;
    }

    public <T> T[] jsonParses(String json, Class<T> c) {
        if (TextUtils.isEmpty(json)) return null;
        try {
            if (json.charAt(0) == '[') {
                return jsonParses(new JSONArray(json), c);
            } else if (json.charAt(0) == '{') {
                Timber.w("please use method jsonParse(json,class)");
            }
        } catch (JSONException e) {
            Timber.w("please use json jsonParse data");
        }
        return null;
    }

    /**
     * 遍历json中的key值，让其与实体类属性名匹配,匹配成功则取出值
     *
     * @param jsonObject 需要解析的json
     * @param field      value的类型
     * @return obj value的值
     */
    public Object getValue(JSONObject jsonObject, Field field) {
        String key = field.getName();
        Class clazz = field.getType();
        try {
            if (clazz == String.class) {
                return jsonObject.getString(key);
            } else if (clazz == boolean.class || clazz == Boolean.class) {
                return jsonObject.getBoolean(key);
            } else if (clazz == int.class || clazz == Integer.class) {
                return jsonObject.getInt(key);
            } else if (clazz == long.class || clazz == Long.class) {
                return jsonObject.getLong(key);
            } else if (clazz == double.class || clazz == Double.class) {
                return jsonObject.getDouble(key);
            } else if (clazz.isArray() || Collection.class.isAssignableFrom(clazz)) {
                return jsonObject.getJSONArray(key);
            } else {
                return jsonObject.getJSONObject(key);
            }
        } catch (Exception e) {
            Timber.w(" getValue:key:" + key + "  json:" + jsonObject.toString());
        }
        return null;
    }

    public <E> E getArrayValue(JSONArray jsonArray, Type type, int position) {
        try {
            if (type == String.class) {
                return (E) jsonArray.getString(position);
            } else if (type == int.class && type == Integer.class) {
                Integer i = jsonArray.getInt(position);
                return (E) i;
            } else if (type == long.class && type == Long.class) {
                Long i = jsonArray.getLong(position);
                return (E) i;
            } else if (type == boolean.class && type == Boolean.class) {
                Boolean i = jsonArray.getBoolean(position);
                return (E) i;
            } else if (type == boolean.class && type == Double.class) {
                Double i = jsonArray.getDouble(position);
                return (E) i;
            } else if (type instanceof Class) {
                Class<E> c = (Class<E>) type;
                if (c.isArray()) return (E)jsonParses(jsonArray.getJSONArray(position), c);
                else if (!Collection.class.isAssignableFrom(c)) {
                    return accept(jsonParse(jsonArray.getJSONObject(position), c));
                }
            } else {
                if (type instanceof ParameterizedType) {
                    Type rawType = ((ParameterizedType) type).getRawType();
                    if (Collection.class.isAssignableFrom((Class) rawType)) {
                        Type type1 = ((ParameterizedType) type).getActualTypeArguments()[0];
                        return (E) Arrays.asList(jsonParses(jsonArray.getJSONArray(position), type1));
                    }else{
                        return jsonParse(jsonArray.getJSONObject(position),rawType);
                    }
                }
            }
        } catch (Exception e) {
            Timber.w(" getArrayValue:key:" + position + "  json:" + jsonArray.toString());
        }
        return null;
    }


    public <T> T jsonParse(JSONObject json, Class<T> c) {
        T entity = ReflectUtil.newInstance(c);
        for (Field field : c.getDeclaredFields()) {
            String fName = field.getName();
            if (TextUtils.isEmpty(fName) || json.isNull(fName)) continue;
            Object object = getValue(json, field);
            if (ReflectUtil.isFieldNull(object)) continue;
            Method declareMethod = ReflectUtil.beanSetMethod(field, c);
            if (ReflectUtil.isBaseType(object)) {
                invoke(declareMethod, entity, object);
            } else if (object instanceof JSONArray) {
                if (c.isArray())
                    invoke(declareMethod, entity, new Object[]{jsonParses((JSONArray) object, c.getComponentType())});
                else {
                    Type type = field.getGenericType();
                    if (type instanceof ParameterizedType)
                        invoke(declareMethod, entity, Arrays.asList(jsonParses((JSONArray) object,
                                ((ParameterizedType) type).getActualTypeArguments()[0])));
                }
            } else {
                invoke(declareMethod, entity, accept(jsonParse((JSONObject) object, field.getType())));
            }
        }
        Timber.v("pares:%1s", entity + "");
        return entity;
}

    public <E> E[] jsonParses(JSONArray jsonArray, Type type) {
        Class<E> c = null;
        if (type instanceof Class)
            c = (Class<E>) type;
        else if (type instanceof ParameterizedType) {
            Type t = ((ParameterizedType) type).getRawType();
            if (t instanceof Class) c = (Class<E>) t;
        } else if (type instanceof GenericArrayType) {
            Type t = ((GenericArrayType) type).getGenericComponentType();
            return getGenericArray(jsonArray, t);
        }
        if (c == null) return (E[]) new Object[0];
        E[] array = (E[]) Array.newInstance(c, jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            array[i] = getArrayValue(jsonArray, type, i);
        }
        Timber.v("pares:%1s", arrayToString(array));
        return array;
    }

    public <E> E[] getGenericArray(JSONArray jsonArray, Type t) {
        try {
            if (jsonArray.length() != 0) {
                E e = (E) jsonParses(jsonArray.getJSONArray(0), t);
                E[] array = (E[]) Array.newInstance(e.getClass(), jsonArray.length());
                array[0] = e;
                for (int i = 1; i < array.length; i++)
                    array[i] = (E) jsonParses(jsonArray.getJSONArray(0), t);
                return array;
            }
        } catch (Exception e) {
            Timber.w(" getArrayValue:t:" + t + "  json:" + jsonArray.toString());
        }
        return (E[]) new Object[0];
    }

    private <E>E accept(E e){
        try {
            if(consumer!=null)consumer.accept(e);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return e;
    }
}
