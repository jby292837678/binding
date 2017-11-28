package com.binding.library.data.encrypt;

import com.binding.library.util.ReflectUtil;

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
        List<Field> fields = ReflectUtil.getAllFields(getClass(), new ArrayList<Field>());
        for (Field field : fields) {
            Object o = ReflectUtil.beanGetValue(field, this);
            if (o == null) continue;
            builder.addEncoded(field.getName(), encrypt(String.valueOf(o)));
        }
        return builder.build();
    }


}
