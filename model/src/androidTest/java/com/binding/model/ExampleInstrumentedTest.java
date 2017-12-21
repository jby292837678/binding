package com.binding.model;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.SparseArray;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.binding.library.test", appContext.getPackageName());
    }

    @Test
    public void sparse(){
        SparseArray<String> array = new SparseArray<>();
        array.put(0,"0");
        array.put(1,"1");
        array.put(2,"2");
        array.put(3,"3");
        array.put(4,"4");
        array.put(5,"5");
        array.put(6,"6");
        array.put(7,"7");
        array.put(8,"8");
        for (int i = 0; i < array.size(); i++) {
            System.out.println("key:"+array.keyAt(i)+"value:"+array.valueAt(i));
        }
    }
}
