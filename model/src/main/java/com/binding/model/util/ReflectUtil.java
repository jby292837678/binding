package com.binding.model.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.binding.model.model.inter.Inflate;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import timber.log.Timber;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：9:37
 * modify developer：  admin
 * modify time：9:37
 * modify remark：
 *
 * @version 2.0
 */

@SuppressWarnings("unchecked")
public class ReflectUtil {

    public static List<Field> getAllFields(Class<?> aClass) {
        return getAllFields(aClass, null);
    }

    public static List<Field> getAllFields(Class<?> aClass, List<Field> fields) {
        if (fields == null) fields = new ArrayList<>();
        if (aClass.getSuperclass() != null) {
            for (Field field : aClass.getDeclaredFields()) {
                if (!Modifier.isFinal(field.getModifiers())) fields.add(field);
            }
            getAllFields(aClass.getSuperclass(), fields);
        }
        return fields;
    }

    public static Class getBaseType(Object o) {
        if (o instanceof Integer) {
            return int.class;
        } else if (o instanceof Double) {
            return double.class;
        } else if (o instanceof Long) {
            return long.class;
        } else if (o instanceof Boolean) {
            return boolean.class;
        } else if (o instanceof Short) {
            return short.class;
        } else if (o instanceof Byte) {
            return byte.class;
        } else if (o instanceof Float) {
            return float.class;
        } else if (o instanceof Character) {
            return char.class;
        }
        return o.getClass();
    }

    public static boolean isFieldNull(Object o) {
        String value = String.valueOf(o);
        if (o == null) {
            return true;
        } else if (o instanceof Integer) {
            return Integer.valueOf(value) == 0;
        } else if (o instanceof Double) {
            return Double.valueOf(value) == 0.0d;
        } else if (o instanceof Long) {
            return Long.valueOf(value) == 0L;
//        } else if (o instanceof Boolean) {
//            return Boolean.valueOf(value);
        } else if (o instanceof Short) {
            return Short.valueOf(value) == 0;
        } else if (o instanceof Byte) {
            return Byte.valueOf(value) == 0;
        } else if (o instanceof Float) {
            return Float.valueOf(value) == 0.0f;
        } else if (o instanceof Character) {
            return (char) o == '\u0000';
        } else if (o instanceof String) {
            return TextUtils.isEmpty(o.toString());
        } else if (o instanceof Collection) {
            return ((Collection) o).isEmpty();
        } else if (o.getClass().isArray()) {
            return ((Object[]) o).length == 0;
        }
        return false;
    }

    private static Method beanMethod(Field f, Class<?> c, String prefix, Class... params) {
        f.setAccessible(true);
        char[] cs = f.getName().toCharArray();
//        char[] cs = name.toCharArray();
        if (cs[0] >= 97 && cs[0] <= 122) cs[0] -= 32;
//        Method method = null;
//        try {
//            method = c.getDeclaredMethod(prefix + String.valueOf(cs), params);
//        } catch (Exception e) {
////            e.printStackTrace();
//        }
        return getAllClassMethod(c, prefix + String.valueOf(cs), params);
    }


    public static Field getField(String fieldName, Class<?> c) {
        if (c == null) return null;
        try {
            return c.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Timber.i(e);
        }
        return null;
    }

    public static <T> T getFieldValue(String fieldName, Object bean) {
        try {
            return getFieldValue(bean.getClass().getDeclaredField(fieldName), bean);
        } catch (NoSuchFieldException e) {
            Timber.i(e);
            return null;
        }
    }

    public static <T> T getFieldValue(Field field, Object bean) {
        if (field == null) return null;
        try {
            field.setAccessible(true);
            return (T) field.get(bean);
        } catch (IllegalAccessException e) {
            Timber.i(e);
            return null;

        }
    }

    public static Type[] getFieldGenericType(Field field) {
        field.setAccessible(true);
        Type fieldType = field.getGenericType();
        if (fieldType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) fieldType;
            return parameterizedType.getActualTypeArguments();
        }
        return new Type[0];
    }


    private boolean isFieldNull(Field f) {
        Object o = null;
        try {
            f.setAccessible(true);
            o = f.get(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return o == null || isFieldNull(o);
    }


    public static Method beanSetMethod(Field f, Class c) {
        return beanMethod(f, c, "set", f.getType());
    }

    public static Method beanGetMethod(Field f, Class c) {
        return beanMethod(f, c, f.getType() == boolean.class ? "is" : "get");
    }

    public static void beanSetValue(Field f, Object bean, Object value) {
        try {
            Method method = beanSetMethod(f, bean.getClass());
            if (method != null) method.invoke(bean, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static Object beanGetValue(Field f, Object bean) {
        try {
            Method method = beanGetMethod(f, bean.getClass());
            if (method != null) return method.invoke(bean);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Type[] getReturnGenericType(Method method) {
        Type classType = method.getGenericReturnType();
        if (classType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) classType;
            return parameterizedType.getActualTypeArguments();
        }
        return new Type[0];
    }


    public static Class getSuperGenericType(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) return Object.class;
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) index = params.length - 1;
        if (!(params[index] instanceof Class) || index == -1) return Object.class;
        return (Class) params[index];
    }

    public static Class getSuperGenericType(Class clazz) {
        return getSuperGenericType(clazz, 0);
    }


    public static Type[] getSuperGenericTypes(Class clazz) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) return new Type[0];
        return ((ParameterizedType) genType).getActualTypeArguments();
    }


    public static List<Class> getGenericType(Field field) {
        List<Class> list = new ArrayList<>();
        Type fieldType = field.getGenericType();
        if (fieldType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) fieldType;
            Type[] fieldArgType = parameterizedType.getActualTypeArguments();
            for (Type type : fieldArgType) {
                list.add((Class<?>) type);
            }
            return list;
        }
        return list;
    }

    public static void invoke(String methodName, Object o, Object... args) {
        Class<?>[] cs = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) cs[i] = args[i].getClass();
        Method method = getAllClassMethod(o.getClass(), methodName, cs);
        if (method != null) {
            method.setAccessible(true);
            invoke(method, o, args);
        } else
            Timber.e("no such method method:%1s \tobject:%2s \t params: %2s", methodName, o.getClass().getName(), arrayToString(args));
    }

    public static Method getAllClassMethod(Class<?> c, String methodName, Class<?>[] cs) {
        if (TextUtils.isEmpty(methodName)) return null;
        Method method = null;
        try {
            method = c.getDeclaredMethod(methodName, cs);
        } catch (Exception e) {
            Timber.e("no such method method:%1s", methodName);
        }
        if (method == null) {
            for (Method declareMethod : c.getDeclaredMethods()) {
                if (isValid(methodName, declareMethod, cs)) {
                    method = declareMethod;
                    break;
                }
            }
        }
        if (method == null && c != Object.class)
            return getAllClassMethod(c.getSuperclass(), methodName, cs);
        return method;
    }

    private static boolean isValid(String methodName, Method declareMethod, Class<?>[] cs) {
        if (!declareMethod.getName().equals(methodName)) return false;
        Class<?>[] params = declareMethod.getParameterTypes();
        if (cs.length != params.length) return false;
        int index = -1;
        for (Class<?> param : params) {
            if(!param.isAssignableFrom(cs[++index]))return false;
        }
        return true;
    }

    public static <E extends Inflate> ArrayList<E> copyList(List<? extends E> list) {
        ArrayList<E> copy = new ArrayList<>();
        for (E e : list)
            copy.add(copy(e));
        return copy;
    }

    public static <E extends Inflate> ArrayList<E> copyList(List<? extends E> list, int modelIndex) {
        ArrayList<E> copy = new ArrayList<>();
        for (E e : list) {
            E c = copy(e);
            c.setModelIndex(modelIndex);
            copy.add(c);
        }
        return copy;
    }

    public static void invoke(Method method, Object t, Object... args) {
        try {
            method.invoke(t, args);
        } catch (Exception e) {
            Timber.e("method:%1s \tobject:%2s \t params: %2s", method.getName(), t.getClass().getName(), arrayToString(args));
        }
    }

    public static String arrayToString(Object[] args) {
        if (args == null) return "";
        StringBuilder builder = new StringBuilder();
        for (Object arg : args) {
            builder.append(arg.getClass().getName());
            builder.append(":");
            builder.append(arg.toString());
            builder.append("\t\t");
        }
        return builder.toString();
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
     * @return the generic types of interface or class
     * for example :
     * interface I<T>{}
     * interface I1<T>{}
     * class Parent<T></> implements I<T>{}
     * class C extends Parent<String> implements I1<Integer>{}
     * AdapterType[] type = getGenericTypes(C.class,I.class)
     * type = {T.class}
     */
    public static Class getGenericTypes(Class clazz, Class<?> c, int index) {
        if (clazz == null) return null;
        Class<?> superClass = clazz.getSuperclass();

        Class<?>[] cls = clazz.getInterfaces();
        for (int i = 0; i < cls.length; i++) {
            if (c.isAssignableFrom(cls[i])) {
                Type genType = clazz.getGenericInterfaces()[i];
                if (genType instanceof ParameterizedType) {
                    Type[] types = ((ParameterizedType) genType).getActualTypeArguments();
                    if (types[index] instanceof Class) {
                        return (Class) types[index];
                    } else if (types[index] instanceof TypeVariable) {
                        return getClass(((TypeVariable) types[index]).getBounds()[0], 0);
                    }
                } else {
                    return null;
                }
            }
        }
        return getGenericTypes(clazz.getSuperclass(), c, index);
    }

    private static Class getGenericClass(ParameterizedType parameterizedType, int i) {
        Object genericClass = parameterizedType.getActualTypeArguments()[i];
        if (genericClass instanceof ParameterizedType) { // 处理多级泛型
            return (Class) ((ParameterizedType) genericClass).getRawType();
        } else if (genericClass instanceof GenericArrayType) { // 处理数组泛型
            return (Class) ((GenericArrayType) genericClass).getGenericComponentType();
        } else if (genericClass instanceof TypeVariable) { // 处理泛型擦拭对象
            return getClass(((TypeVariable) genericClass).getBounds()[0], 0);
        } else {
            return (Class) genericClass;
        }
    }

    private static Class getClass(Type type, int i) {
        if (type instanceof ParameterizedType) { // 处理泛型类型
            return getGenericClass((ParameterizedType) type, i);
        } else if (type instanceof TypeVariable) {
            return getClass(((TypeVariable) type).getBounds()[0], 0); // 处理泛型擦拭对象
        } else {// class本身也是type，强制转型
            return (Class) type;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T transform(Object entity, Class<T> tClass) {
        if (entity instanceof List) {
            List collection = (List) entity;
            if (collection.isEmpty()) return null;
            else return (T) transformList(collection, collection.get(0).getClass());
        } else {
            T t = newInstance(tClass);
            for (Field f : entity.getClass().getDeclaredFields()) {
                try {
                    Field tf = tClass.getDeclaredField(f.getName());
                    Object value = ReflectUtil.beanGetValue(tf, entity);
                    ReflectUtil.beanSetValue(tf, t, value);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
            return t;
        }
    }


    public static <T> List<T> transformList(Collection<?> objects, Class<T> tClass) {
        List<T> ts = new ArrayList<>();
        for (Object entity : objects) ts.add(transform(entity, tClass));
        return ts;
    }

    public static <T> T newInstance(Class<T> tClass, Object... args) {
        Class[] cs = new Class[args.length];
        for (int i = 0; i < args.length; i++) cs[i] = args[i].getClass();
        try {
            if (args.length == 0) return tClass.newInstance();
            else {
                return tClass.getConstructor(cs).newInstance(args);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static <T> T copy(@NonNull T t) {
        Class<T> c = (Class<T>) t.getClass();
        T t1 = newInstance(c);
        for (Field field : getAllFields(t.getClass())) {
            Object object = beanGetValue(field, t);
            if (!isFieldNull(object)) beanSetValue(field, t1, object);
        }
        return t1;
    }


    public static <F, T> T trans(@NonNull F f, Class<T> tc) {
        Class<F> fc = (Class<F>) f.getClass();
        T t = newInstance(tc);
        for (Field field : getAllFields(tc)) {
            try {
                Field fField = fc.getDeclaredField(field.getName());
                Object o = beanGetValue(fField, f);
                if (!isFieldNull(o)) beanSetValue(field, t, o);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return t;
    }


    public <T> T copy(T t, Object... args) {
        T coyp = newInstance((Class<T>) t.getClass(), args);
        for (Field field : ReflectUtil.getAllFields(getClass())) {
            Object object = ReflectUtil.beanGetValue(field, this);
            if (object != null)
                ReflectUtil.beanSetValue(field, coyp, object);
        }
        return coyp;
    }


    public static Object getField(Class<?> c, String feildName) {
        Object o = ReflectUtil.newInstance(c);
        Field f = ReflectUtil.getField(feildName, c);
        try {
            return f.get(o);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
