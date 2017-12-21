package com.binding.model;

import android.util.SparseArray;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    public static <T> T newInstance(Class<T> tClass, Object... args) {
        Class[] cs = new Class[args.length];
        for (int i = 0; i < args.length; i++) cs[i] = args[i].getClass();
        try {
            if (args.length == 0)
                return tClass.newInstance();
            else {
                Constructor<T> constructor = tClass.getConstructor(cs);
                return constructor.newInstance(args);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void create() {
        A a = newInstance(A.class, 1);
        assertEquals(1, a.getA());
    }

    @Test
    public void pos() throws InterruptedException {
        List<Integer> list = new ArrayList<>();
//        list.add(0);
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        for (Integer pos :list) {
//            System.out.println(pos);
//        }
//
//        Thread.sleep(100);
//        list.add(0,10);
//        list.add(1,11);
//        list.add(2,12);
//        list.add(3,13);
//        for (Integer pos :list) {
//            System.out.println(pos);
//        }

        System.out.println(list.indexOf(100));
    }
}