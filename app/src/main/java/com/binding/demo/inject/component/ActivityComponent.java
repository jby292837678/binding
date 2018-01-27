package com.binding.demo.inject.component;


import com.binding.demo.inject.scope.ActivityScope;
import com.binding.demo.inject.module.ActivityModule;
import com.binding.demo.ui.bottom.BottomSheetActivity;
import com.binding.demo.ui.home.HomeActivity;
import com.binding.demo.ui.message.MessageActivity;
import com.binding.demo.ui.password.PasswordActivity;
import com.binding.demo.ui.select.SelectActivity;
import com.binding.demo.ui.start.StartupActivity;
import com.binding.demo.ui.user.login.LoginActivity;
import com.binding.demo.ui.user.register.RegisterActivity;

import dagger.Component;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：14:28
 * modify developer：  admin
 * modify time：14:28
 * modify remark：
 *
 * @version 2.0
 */
@ActivityScope
@Component(dependencies = AppComponent.class,modules={ActivityModule.class})
public interface ActivityComponent {
    void inject(HomeActivity activity);
    void inject(LoginActivity activity);
    void inject(RegisterActivity activity);
    void inject(StartupActivity activity);
    void inject(MessageActivity activity);
    void inject(PasswordActivity activity);
    void inject(SelectActivity activity);
    void inject(BottomSheetActivity activity);


    interface Router {
        String ike = "/ike/";
        String login = ike +"login";
        String home = ike +"home";
        String register = ike +"register";
        String startup = ike +"startup";

        String password = ike+"password";
        String message = ike+"message";
        String bottom_sheet = ike+"bottom/sheet";
        String select = ike+"select";
    }
}
