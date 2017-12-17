package com.binding.model.model.inter;

import io.reactivex.Flowable;

/**
 * Created by arvin on 2017/11/30.
 */

public interface HttpFlow<R> {
    Flowable<R> http(int offset, boolean refresh);
}
