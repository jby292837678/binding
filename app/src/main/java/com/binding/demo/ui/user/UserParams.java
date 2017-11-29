package com.binding.demo.ui.user;

import com.binding.model.data.encrypt.aes.AESParams;

/**
 * Created by arvin on 2017/11/28.
 */

public class UserParams extends AESParams {
    private int is_app = 1;
    private String account;
    private String password;

    public int getIs_app() {
        return is_app;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
