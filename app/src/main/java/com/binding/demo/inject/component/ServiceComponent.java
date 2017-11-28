package com.binding.demo.inject.component;

/**
 * Created by pc on 2017/9/4.
 */

import com.binding.library.inject.scope.ServiceScope;
import com.binding.demo.inject.module.ServiceModule;

import dagger.Component;

@ServiceScope
@Component(dependencies = AppComponent.class, modules = {ServiceModule.class})
public interface ServiceComponent {
//    void inject(UpdateService service);
//    void inject(ResourceService service);
}
