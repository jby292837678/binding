package com.binding.demo.inject.interceptor;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.binding.demo.ui.user.User;

import timber.log.Timber;

/**
 * Created by apple on 2017/7/30.
 */

public class RouterInterceptor implements IInterceptor{
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        if(User.isLogin){
            callback.onContinue(postcard);
        }else{
            callback.onInterrupt(null);
        }
    }

    @Override
    public void init(Context context) {
        Timber.i("init router interceptor");
    }
}
