package com.binding.demo.ui.start;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.binding.demo.base.cycle.BaseActivity;

import static com.binding.demo.inject.component.ActivityComponent.Router.startup;

/**
 * Created by arvin on 2017/11/28.
 */

@Route(path = startup)
public class StartupActivity extends BaseActivity <StartupModel>{

}
