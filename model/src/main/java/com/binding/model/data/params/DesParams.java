package com.binding.model.data.params;


import com.binding.model.data.encrypt.des.DES;

/**
 * Created by apple on 17/1/6.
 */

public class DesParams extends DknbEncryptParams {
    @Override
    public String encrypt(String json) {
        DES crypt = new DES(DES.DKDBKEY);
        return crypt.encrypt(json);
    }
}
