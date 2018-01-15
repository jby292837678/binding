package com.binding.demo.ui.password;

import android.os.Bundle;

import com.binding.demo.R;
import com.binding.demo.databinding.ActivityPasswordBinding;
import com.binding.model.model.ModelView;
import com.binding.model.model.ViewModel;

import javax.inject.Inject;

/**
 * Created by arvin on 2018/1/10.
 */

@ModelView(R.layout.activity_password)
public class PasswordModel extends ViewModel<PasswordActivity,ActivityPasswordBinding>{
    @Inject PasswordModel() {}

    @Override
    public void attachView(Bundle savedInstanceState, PasswordActivity passwordActivity) {
        super.attachView(savedInstanceState, passwordActivity);
    }
}
