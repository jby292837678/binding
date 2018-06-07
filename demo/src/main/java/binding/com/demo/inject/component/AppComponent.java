package binding.com.demo.inject.component;

import android.content.Context;
import android.content.res.Resources;

import binding.com.demo.inject.qualifier.context.AppContext;
import binding.com.demo.inject.scope.ApplicationScope;
import binding.com.demo.inject.module.AppModule;
import binding.com.demo.inject.module.DataModule;
import binding.com.demo.inject.module.NetWorkModule;

import dagger.Component;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：10:08
 * modify developer：  admin
 * modify time：10:08
 * modify remark：
 *
 * @version 2.0
 */

@ApplicationScope
@Component(modules={AppModule.class, NetWorkModule.class,DataModule.class})
public interface AppComponent {
    @AppContext
    Context context();
    Resources resources();
//    ReadApi getReadApi();
//    @UserSharePreference
//    SharePreferenceUtil NingUtil();
}
