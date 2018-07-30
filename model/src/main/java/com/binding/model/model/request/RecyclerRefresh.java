package com.binding.model.model.request;


import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        RecyclerStatus.init,
        RecyclerStatus.resumeError,
        RecyclerStatus.click,
        RecyclerStatus.loadTop,
        RecyclerStatus.loadBottom,
})
public @interface RecyclerRefresh {
}
