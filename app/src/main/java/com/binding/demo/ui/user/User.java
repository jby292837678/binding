package com.binding.demo.ui.user;

import android.content.Context;
import android.text.TextUtils;

import com.binding.library.data.save.SharePreferenceUtil;


/**
 * Created by arvin on 2017/11/27.
 */

public class User {
    public static boolean isLogin = false;

    private SharePreferenceUtil util;
    private final UserEntity userEntity;

    public User(Context context) {
        util = SharePreferenceUtil.getUserInstance(context);
        userEntity = util.getAllDto(UserEntity.class);
        isLogin = !TextUtils.isEmpty(userEntity.getToken());
    }

    public void set(UserEntity entity){
        userEntity.setEntity(entity);
        util.setAllDto(entity);
    }

    public void setToken(String token) {
        userEntity.setToken(token);
        util.setValue("token",token);
    }

    public String getToken() {
        return userEntity.getToken();
    }

    public String getMac_address() {
        return userEntity.getMac_address();
    }

    public void setMac_address(String mac_address) {
        userEntity.setMac_address(mac_address);
        util.setValue("mac_address",mac_address);
    }
    public int getId() {
        return userEntity.getId();
    }

    public void setId(int id) {
        userEntity.setId(id);
        util.setValue("id",id);
    }

    public long getTime() {
        return userEntity.getTime();
    }

    public void setTime(long time) {
        userEntity.setTime(time);
        util.setValue("time",time);
    }

    public String getHeadimage_url() {
        return userEntity.getHeadimage_url();
    }

    public void setHeadimage_url(String headimage_url) {
        userEntity.setHeadimage_url(headimage_url);
        util.setValue("headimage_url",headimage_url);
    }


    public String getEmail() {
        return userEntity.getEmail();
    }

    public void setEmail(String email) {
        userEntity.setEmail(email);
        util.setValue("email",email);
    }

    public String getAccount() {
        return userEntity.getAccount();
    }

    public void setAccount(String account) {
        userEntity.setAccount(account);
        util.setValue("account",account);
    }

}
