package com.binding.library.data.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import timber.log.Timber;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：15:18
 * modify developer：  admin
 * modify time：15:18
 * modify remark：
 *
 * @version 2.0
 */


public class JsonConverterFactory extends Converter.Factory {
    public static JsonConverterFactory create() {
        return create(new Gson());
    }
    public static JsonConverterFactory create(Gson gson) {
        return new JsonConverterFactory(gson);
    }

    private Gson gson;


    private JsonConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        Timber.i("annotations:%1s, type=%2s ,retrofit = ",annotations.length,type.getClass().getName());
        return new JsonResponseBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
//        if(type instanceof TypeParams){
//        Timber.i("request:%1s",gson.toJson(type));
        Timber.i("param:%1s",parameterAnnotations.length);
        Timber.i("method:%1s",methodAnnotations.length);
        Timber.i("type:%1s",type);

        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new JsonRequestBodyConverter<>(gson, adapter);
    }
}
