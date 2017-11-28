package com.binding.demo.ui.user;

/**
 * Created by arvin on 2017/11/27.
 */

public class UserEntity {
    private String token;
    private String mac_address;
    private int id;
    private long time;
    private String headimage_url, email, account;

    void setToken(String token) {
        this.token = token;
    }

    String getToken() {
        return token;
    }

    String getMac_address() {
        return mac_address;
    }

    void setMac_address(String mac_address) {
        this.mac_address = mac_address;
    }


    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    long getTime() {
        return time;
    }

    void setTime(long time) {
        this.time = time;
    }

    String getHeadimage_url() {
        return headimage_url;
    }

    void setHeadimage_url(String headimage_url) {
        this.headimage_url = headimage_url;
    }

    String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    String getAccount() {
        return account;
    }

    void setAccount(String account) {
        this.account = account;
    }

    public void setEntity(UserEntity entity) {
        this.token = entity.token;
        this.mac_address = entity.mac_address;
        this.id = entity.id;
        this.time = entity.time;
        this.headimage_url = entity.headimage_url;
        this.email = entity.email;
        this.account = entity.account;
    }
}
