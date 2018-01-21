package com.binding.demo.inject.component;

import com.binding.demo.inject.module.FragmentModule;
import com.binding.demo.inject.scope.FragmentScope;
import com.binding.demo.ui.message.fragment.MessageFragment;

import dagger.Component;


/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：11:29
 * modify developer：  admin
 * modify time：11:29
 * modify remark：
 *
 * @version 2.0
 */

@FragmentScope
@Component(dependencies = AppComponent.class,modules={FragmentModule.class})
public interface FragmentComponent {
    void inject(MessageFragment fragment);
}
