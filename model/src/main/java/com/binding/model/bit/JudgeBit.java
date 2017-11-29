package com.binding.model.bit;

/**
 * Created by arvin on 2017/11/28.
 */

public interface JudgeBit<T,R> {
    R judge(int position,T t,R p);
}
