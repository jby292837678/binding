package binding.com.demo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DataEntity {
    private int a = 19;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }


    public Method getMethod(){
        try {
            return DataEntity.class.getDeclaredMethod("getA");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getMethodValue(){
        try {
              return getMethod().invoke(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return "";
    }

    public  Field getEntityField(){
        try {
            return DataEntity.class.getDeclaredField("a");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }return null;
    }

    public Object getValue(){
        try {
            Field field = getEntityField();
            field.setAccessible(true);
            return field.get(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return "error a";
    }


}
