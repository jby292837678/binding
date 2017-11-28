package com.binding.library.adapter;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        AdapterType.add,
        AdapterType.refresh,
        AdapterType.remove,
        AdapterType.set,
        AdapterType.select,
        AdapterType.no,
})
public @interface AdapterHandle {
}