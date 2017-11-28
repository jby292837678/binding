package com.binding.demo.ui.user.register;

import com.binding.library.model.ModelView;
import com.binding.library.model.ViewModel;
import com.binding.demo.R;
import com.binding.demo.databinding.ActivityRegisterBinding;

import javax.inject.Inject;

/**
 * Created by arvin on 2017/11/28.
 */


@ModelView(R.layout.activity_register)
public class RegisterModel extends ViewModel<RegisterActivity,ActivityRegisterBinding>{
    @Inject RegisterModel(){}
}
