package com.binding.demo.inject.api;

import com.binding.demo.ui.user.UserEntity;
import com.binding.library.data.entity.InfoEntity;

import io.reactivex.Flowable;
import retrofit2.http.POST;

/**
 * Created by arvin on 2017/11/28.
 */

public interface Api {
    String host = "";

    @POST("paladin1/Passport/dologin")
    Flowable<InfoEntity<UserEntity>> login();

}
