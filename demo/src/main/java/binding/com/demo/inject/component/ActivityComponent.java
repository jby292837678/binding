package binding.com.demo.inject.component;


import binding.com.demo.inject.scope.ActivityScope;
import binding.com.demo.inject.module.ActivityModule;

import binding.com.demo.ui.address.AddressActivity;
import binding.com.demo.ui.address.edit.AddressEditActivity;
import binding.com.demo.ui.drag.DragActivity;
import binding.com.demo.ui.home.HomeActivity;
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
    void inject(AddressActivity activity);
    void inject(AddressEditActivity activity);
    void inject(DragActivity activity);


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
        String main = ike+"main";
        String address = ike+"address/list";
        String address_edit = address+"/edit";
        String drag = ike+"drag";
    }
}
