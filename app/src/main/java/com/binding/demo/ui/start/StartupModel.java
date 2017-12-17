package com.binding.demo.ui.start;

import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.binding.demo.base.arouter.ArouterUtil;
import com.binding.model.model.ModelView;
import com.binding.model.model.ViewModel;
import com.binding.demo.R;
import com.binding.demo.databinding.ActivityStartupBinding;
import com.binding.model.util.BaseUtil;

import javax.inject.Inject;

import static com.binding.demo.inject.component.ActivityComponent.Router.home;

/**
 * Created by arvin on 2017/11/28.
 */

@ModelView(R.layout.activity_startup)
public class StartupModel extends ViewModel<StartupActivity,ActivityStartupBinding>{
    @Inject StartupModel() {}

    @Override
    public void attachView(Bundle savedInstanceState, StartupActivity startupActivity) {
        super.attachView(savedInstanceState, startupActivity);
        ArouterUtil.navigation(home);
    }
}
