package com.binding.model.data.encrypt.des;

import com.binding.model.data.encrypt.FormUnionParams;

/**
 * Created by apple on 17/1/6.
 */

public class DesParams extends FormUnionParams {

    @Override
    public String encrypt(Object obj) {
        String json = obj.toString();
        DES crypt = new DES(DES.DKDBKEY);
        return crypt.encrypt(json);
    }

    @Override
    public String getKey() {
        return "data";
    }
}
