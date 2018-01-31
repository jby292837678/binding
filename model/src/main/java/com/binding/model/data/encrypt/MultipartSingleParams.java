package com.binding.model.data.encrypt;

import com.binding.model.util.BaseUtil;
import com.binding.model.util.ReflectUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by pc on 2017/9/6.
 */

public abstract class MultipartSingleParams implements SingleTransParams<MultipartBody> {

    @Override
    public MultipartBody transParams() {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (Field field : ReflectUtil.getAllFields(getClass(), new ArrayList<Field>())) {
            Object o = ReflectUtil.beanGetValue(field, this);
            if (o == null) continue;
            if (o instanceof File ) {
                File file = (File)o;
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
                builder.addFormDataPart(BaseUtil.findQuery(field), BaseUtil.findQuery(field), requestBody);
            }else if( o instanceof File[]){
                for(File file :(File[])o){
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
                    builder.addFormDataPart(BaseUtil.findQuery(field), file.getName(), requestBody);
                }
            } else {
                builder.addFormDataPart(BaseUtil.findQuery(field),encrypt(o));
            }
        }
        return builder.build();
    }


}
