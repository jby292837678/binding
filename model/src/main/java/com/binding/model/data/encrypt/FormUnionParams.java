package com.binding.model.data.encrypt;

import android.text.TextUtils;

import com.binding.model.util.BaseUtil;
import com.google.gson.Gson;
import com.binding.model.util.ReflectUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.FormBody;


/**
 * Created by pc on 2017/9/6.
 */

public abstract class FormUnionParams extends FormSingleParams implements UnionTransParams<FormBody> {
    @Override
    public FormBody transParams() {
        if(TextUtils.isEmpty(getKey()))return super.transParams();
        FormBody.Builder builder = new FormBody.Builder();
        HashMap<String, Object> hashMap = new HashMap<>();
        for (Field field : ReflectUtil.getAllFields(getClass(), new ArrayList<Field>())) {
            Object o = ReflectUtil.beanGetValue(field, this);
            if (o == null) continue;
            hashMap.put(BaseUtil.findQuery(field), o);
        }
        builder.add(getKey(), encrypt(new Gson().toJson(hashMap)));
        return builder.build();
    }
}
