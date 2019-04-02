package com.binding.model.adapter;

import androidx.annotation.IntDef;

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
        AdapterType.refreshAsync,
})
public @interface AdapterHandle {
}