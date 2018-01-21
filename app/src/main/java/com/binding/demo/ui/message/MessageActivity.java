package com.binding.demo.ui.message;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.binding.demo.base.cycle.BaseActivity;
import com.binding.demo.inject.component.ActivityComponent;

/**
 * Created by arvin on 2018/1/21.
 */

@Route(path = ActivityComponent.Router.message)
public class MessageActivity extends BaseActivity<MessageModel> {
}
