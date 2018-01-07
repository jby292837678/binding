package com.binding.model.model.inter;

import io.reactivex.Observable;

/**
 * Created by arvin on 2018/1/7.
 */

public interface HttpObservableRefresh<R> {
    Observable<R> http(int offset, boolean refresh);
}
