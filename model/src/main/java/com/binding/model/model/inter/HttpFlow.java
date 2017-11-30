package com.binding.model.model.inter;

import io.reactivex.Observable;

/**
 * Created by arvin on 2017/11/30.
 */

public interface HttpFlow<R> {
    Observable<R> http(int offset, boolean refresh);
}
