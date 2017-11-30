package com.binding.model.model.inter;


import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：14:29
 * modify developer：  admin
 * modify time：14:29
 * modify remark：
 *
 * @version 2.0
 * @param <R>
 */


public interface Http<R> {
    Observable<R> http(int offset, boolean refresh);
//    Flowable<R> http(int offset,boolean refresh);
}
