package com.binding.library.data.encrypt.des;

import com.binding.library.data.encrypt.FormUnionParams;

/**
 * Created by apple on 17/1/6.
 */

public class DesParams extends FormUnionParams {

    @Override
    public String encrypt(String json) {
        DES crypt = new DES(DES.DKDBKEY);
        return crypt.encrypt(json);
    }

    @Override
    public String getKey() {
        return "data";
    }
}
