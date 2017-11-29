package com.binding.demo.ui.start;

import com.binding.model.model.ModelView;
import com.binding.model.model.ViewModel;
import com.binding.demo.R;
import com.binding.demo.databinding.ActivityStartupBinding;

import javax.inject.Inject;

/**
 * Created by arvin on 2017/11/28.
 */

@ModelView(R.layout.activity_startup)
public class StartupModel extends ViewModel<StartupActivity,ActivityStartupBinding>{
    @Inject StartupModel() {}

}
