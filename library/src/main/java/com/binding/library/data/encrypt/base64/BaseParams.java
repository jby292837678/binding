package com.binding.library.data.encrypt.base64;

import android.util.Base64;

import com.binding.library.data.encrypt.FormUnionParams;

/**
 * Created by apple on 17/1/6.
 */

public class BaseParams extends FormUnionParams {

    @Override
    public String encrypt(String json) {
        byte[] data = Base64.encode(json.getBytes(), Base64.NO_WRAP);
        return new String(data, Base64.NO_WRAP);
    }

    @Override
    public String getKey() {
        return "data";
    }
}
