package com.binding.model.data.encrypt.aes;

import com.binding.model.data.encrypt.FormUnionParams;

/**
 * Created by arvin on 2017/11/28.
 */

public class AESParams extends FormUnionParams {

    @Override
    public String encrypt(Object json) {
        return json.toString();
    }

    @Override
    public String getKey() {
        return "";
    }

}
