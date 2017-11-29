package com.binding.model.data.encrypt.rsa;

import android.text.TextUtils;

import com.binding.model.BuildConfig;
import com.binding.model.data.encrypt.FormUnionParams;

import timber.log.Timber;

/**
 * Created by pc on 2017/9/6.
 */

public class RSAParams extends FormUnionParams {

    @Override
    public String encrypt(String json) {
        if(BuildConfig.DEBUG)return json;
        String encrypt = RSA.encryptByPublic(json);
        Timber.i("first : json:%1s encrypt:%2s",json,encrypt);
        if (!TextUtils.isEmpty(encrypt)){
            encrypt = encrypt.replaceAll("\n","")
                             .replaceAll("\t","")
                             .replaceAll("\r","");
        }
        return encrypt;
    }

    @Override
    public String getKey() {
        return BuildConfig.DEBUG?"":"params";
    }
}
