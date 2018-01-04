package com.binding.model.model.inter;

/**
 * Created by arvin on 2017/12/31.
 */

public interface Http<R> {
    R http(int offset,boolean refresh);
}
