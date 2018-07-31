package com.binding.model.data.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.binding.model.util.ReflectUtil;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.sql.Ref;
import java.util.Arrays;
import java.util.Collection;

import io.reactivex.functions.Consumer;
import timber.log.Timber;

import static com.binding.model.util.ReflectUtil.arrayToString;
import static com.binding.model.util.ReflectUtil.getActualTypeArguments;
import static com.binding.model.util.ReflectUtil.invoke;

@SuppressWarnings("unchecked")
@SuppressLint("DefaultLocale")
public class JsonDeepUtil {
    private static JsonDeepUtil jsonDeepUtil = new JsonDeepUtil();
    private Consumer<Object> consumer;


    public static <T> T parse(String json, Type type) {
        return jsonDeepUtil.jsonParse(json, type);
    }

    public static <T> T[] parses(String json, Type c) {
        return jsonDeepUtil.jsonParses(json, c);
    }

    public void setConsumer(Consumer<Object> consumer) {
        this.consumer = consumer;
    }

    public <T> T jsonParse(String json, Type type) {
        if (TextUtils.isEmpty(json)) return null;
        try {
            if (json.trim().charAt(0) == '{') {
                return accept(jsonParse(new JSONObject(json), type));
            } else if (json.trim().charAt(0) == '[') {
                Timber.w("please use method jsonParses(json,type)");
            }
        } catch (JSONException e) {
            Timber.w("please use json jsonParse data e:%1s", e.getMessage());
        }
        return null;
    }

    public <T> T[] jsonParses(String json, Type c) {
        if (TextUtils.isEmpty(json)) return null;
        try {
            if (json.charAt(0) == '[') {
                return jsonParses(new JSONArray(json), c);
            } else if (json.charAt(0) == '{') {
                Timber.w("please use method jsonParse(json,class)");
            }
        } catch (JSONException e) {
            Timber.w("please use json jsonParse data e:%1s", e.getMessage());
        }
        return null;
    }

    /**
     * 遍历json中的key值，让其与实体类属性名匹配,匹配成功则取出值
     *
     * @param jsonObject 需要解析的json
     * @param clazz      value的类型
     * @param key        key
     * @return obj value的值
     */
    public Object getValue(JSONObject jsonObject, String key, Class clazz) {
//        Timber.i("key:%1s type:%2s", key, clazz.getSimpleName());
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
            Timber.w(" getValue:key:%1s msg:%2s type:%3s json:%4s",
                    key, e.getMessage(), clazz.getTypeName(), jsonObject.toString());
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
                if (c.isArray()) return (E) jsonParses(jsonArray.getJSONArray(position), c);
                else if (!Collection.class.isAssignableFrom(c)) {
                    return accept(jsonParse(jsonArray.getJSONObject(position), c));
                }
            } else {
                if (type instanceof ParameterizedType) {
                    Type rawType = ((ParameterizedType) type).getRawType();
                    if (Collection.class.isAssignableFrom((Class) rawType)) {
                        return (E) Arrays.asList(jsonParses(jsonArray.getJSONArray(position), getActualTypeArguments(type, 0)));
                    } else {
                        return accept(jsonParse(jsonArray.getJSONObject(position), rawType));
                    }
                }
            }
        } catch (Exception e) {
            Timber.w("getArrayValue:position:%1s msg:%2s type:%3s json:%4s", position, e.getMessage(), type.getTypeName(), jsonArray.toString());
        }
        return null;
    }

    public <T> T jsonParse(JSONObject json, Type type) {
        Class<T> c;
        Type typeArguments = null;
        if (type instanceof Class) {
            c = (Class<T>) type;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            c = (Class<T>) parameterizedType.getRawType();
            typeArguments = parameterizedType.getActualTypeArguments()[0];
        } else return null;
        T entity = ReflectUtil.newInstance(c);
        for (Field field : c.getDeclaredFields()) {
            String fName = field.getName();
            if (json.isNull(fieldName(field))) continue;
//            field.getGenericType() instanceof TypeVariable ? typeArguments:field.getGenericType()
            Class<?> fieldClass = field.getType();
            if (field.getGenericType() instanceof TypeVariable) {
                if (typeArguments instanceof Class) {
                    fieldClass = (Class<?>) typeArguments;
                } else if (typeArguments instanceof ParameterizedType) {
                    fieldClass = (Class<?>) ((ParameterizedType) typeArguments).getRawType();
                }
            }
            Object object = getValue(json, fieldName(field), fieldClass);
            if (ReflectUtil.isFieldNull(object)) continue;
            Method method = ReflectUtil.beanSetMethod(field, c);
            Type genericType = field.getGenericType();
            if (object instanceof JSONObject) {
                typeArguments = (genericType instanceof TypeVariable) ? typeArguments : genericType;
                object = accept(jsonParse((JSONObject) object, typeArguments));
            } else if (object instanceof JSONArray) {
                if (genericType instanceof Class && ((Class) genericType).isArray()) {
                    typeArguments = ((Class) genericType).getComponentType();
                    object = new Object[]{jsonParses((JSONArray) object, typeArguments)};
                } else if (genericType instanceof GenericArrayType) {
                    typeArguments = ((GenericArrayType) genericType).getGenericComponentType();
                    object = jsonParses((JSONArray) object, typeArguments);
                } else {
                    if(typeArguments != null){
                        typeArguments = getActualTypeArguments(typeArguments, 0);
                    }else if (genericType instanceof ParameterizedType) {
                        typeArguments = getActualTypeArguments(genericType, 0);
                    }
                    object = Arrays.asList(jsonParses((JSONArray) object, typeArguments));
                }
            }
            ReflectUtil.invoke(method, entity, object);
        }
        return entity;
    }

    private String fieldName(Field field) {
        SerializedName serializedName = field.getAnnotation(SerializedName.class);
        return serializedName == null ? field.getName() : serializedName.value();
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
            Timber.w(" getGenericArray: msg:%1s json:%2s,type:%3s", e.getMessage(), jsonArray.toString(), t.getTypeName());
        }
        return (E[]) new Object[0];
    }

    private <E> E accept(E e) {
        try {
            if (consumer != null) consumer.accept(e);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return e;
    }
}