package com.binding.model.data.encrypt;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.binding.model.util.ReflectUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by pc on 2017/9/6.
 */

public abstract class MultipartUnionParams extends MultipartSingleParams implements UnionTransParams<MultipartBody> {

    @Override
    public MultipartBody transParams() {
        if(TextUtils.isEmpty(getKey()))return super.transParams();
        HashMap<String, Object> hashMap = new HashMap<>();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (Field field : ReflectUtil.getAllFields(getClass(), new ArrayList<Field>())) {
            Object o = ReflectUtil.beanGetValue(field, this);
            if (o == null) continue;
            if (o instanceof File) {
                File file = (File) o;
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                builder.addFormDataPart(field.getName(), file.getName(), requestBody);
            } else if (o instanceof File[]) {
                for (File file : (File[]) o) {
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    builder.addFormDataPart(field.getName(), file.getName(), requestBody);
                }
            } else {
                hashMap.put(field.getName(), o);
            }
        }
        builder.addFormDataPart(getKey(), encrypt(new Gson().toJson(hashMap)));
        return builder.build();
    }

}
