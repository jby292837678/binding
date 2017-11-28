package com.binding.demo.inject.module;

import com.binding.demo.inject.api.Api;
import com.binding.library.BuildConfig;
import com.binding.library.data.converter.JsonConverterFactory;
import com.binding.library.inject.scope.ApplicationScope;
import com.binding.demo.inject.interceptor.UserInterceptor;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：10:32
 * modify developer：  admin
 * modify time：10:32
 * modify remark：
 *
 * @version 2.0
 */
@Module
public class NetWorkModule {
//        Cache cache = new Cache(new File(),1024*1024*20);


    @Provides
    @ApplicationScope
    Api provideReadApi(OkHttpClient okHttpClient, UserInterceptor userInterceptor) {
        OkHttpClient client = okHttpClient.newBuilder()
                .addInterceptor(userInterceptor).build();

        return new Retrofit.Builder()
                .baseUrl(Api.host)
                .addConverterFactory(JsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .callFactory(client)
                .build().create(Api.class);
    }

    @Provides
    @ApplicationScope
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor())
//                .cache(new Cache(new File(""),1024*1024*20))
                .build().newBuilder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(loggingInterceptor);
        }
        return httpClientBuilder.build();
    }

}
