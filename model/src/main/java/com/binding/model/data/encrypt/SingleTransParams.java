package com.binding.model.data.encrypt;

import okhttp3.RequestBody;

/**
 * Created by pc on 2017/9/6.
 */

public interface SingleTransParams<T extends RequestBody> {
    T transParams();
    String encrypt(Object json);
}
