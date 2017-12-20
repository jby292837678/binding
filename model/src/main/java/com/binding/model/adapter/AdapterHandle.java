package com.binding.model.adapter;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        AdapterType.add,
        AdapterType.refresh,
        AdapterType.remove,
        AdapterType.set,
        AdapterType.move,
        AdapterType.select,
        AdapterType.no,
        AdapterType.onClick,
        AdapterType.onLongClick,
})
public @interface AdapterHandle {
}