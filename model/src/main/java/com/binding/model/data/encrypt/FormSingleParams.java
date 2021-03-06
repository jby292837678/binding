package com.binding.model.data.encrypt;

import com.binding.model.util.BaseUtil;
import com.binding.model.util.ReflectUtil;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;

/**
 * Created by pc on 2017/9/6.
 */

public abstract class FormSingleParams implements SingleTransParams<FormBody> {

    @Override
    public FormBody transParams() {
        FormBody.Builder builder = new FormBody.Builder();
        List<Field> fields = ReflectUtil.getAllFields(getClass(), new ArrayList<>());
        for (Field field : fields) {
            Object o = ReflectUtil.beanGetValue(field, this);
            if (o == null) continue;
            if(BaseUtil.isEncoded(field)) builder.addEncoded(BaseUtil.findQuery(field), encrypt(o));
            else builder.add(BaseUtil.findQuery(field), encrypt(o));
        }
        return builder.build();
    }

//    public static String findQuery(Field field) {
//        Params key = field.getAnnotation(Params.class);
//        return key==null?field.getName():key.value();
//    }

}
