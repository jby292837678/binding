package com.binding.library.data.encrypt;

import okhttp3.RequestBody;

/**
 * Created by apple on 17/1/20.
 */

public interface UnionTransParams<T extends RequestBody> extends SingleTransParams<T> {
     String getKey();
}
