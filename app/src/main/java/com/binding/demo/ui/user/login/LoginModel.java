package com.binding.demo.ui.user.login;

import com.binding.model.model.ModelView;
import com.binding.model.model.ViewModel;
import com.binding.demo.R;
import com.binding.demo.databinding.ActivityLoginBinding;

import javax.inject.Inject;

/**
 * Created by arvin on 2017/11/28.
 */

@ModelView(R.layout.activity_login)
public class LoginModel extends ViewModel<LoginActivity,ActivityLoginBinding>{
    @Inject LoginModel(){}
}
