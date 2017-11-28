package com.binding.library.data.encrypt.aes;

import com.binding.library.data.encrypt.FormUnionParams;

/**
 * Created by arvin on 2017/11/28.
 */

public class AESParams extends FormUnionParams {

    @Override
    public String encrypt(String json) {
        return json;
    }

    @Override
    public String getKey() {
        return "";
    }

}
