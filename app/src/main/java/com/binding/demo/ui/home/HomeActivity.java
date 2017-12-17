package com.binding.demo.ui.home;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.binding.demo.base.cycle.BaseActivity;

import static com.binding.demo.inject.component.ActivityComponent.Router.home;

/**
 * Created by arvin on 2017/11/27.
 */

@Route(path = home)
public class HomeActivity extends BaseActivity<HomeModel> {

}
