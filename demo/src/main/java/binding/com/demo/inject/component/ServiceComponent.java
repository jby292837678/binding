package binding.com.demo.inject.component;

/**
 * Created by pc on 2017/9/4.
 */

import binding.com.demo.inject.scope.ServiceScope;
import binding.com.demo.inject.module.ServiceModule;

import dagger.Component;

@ServiceScope
@Component(dependencies = AppComponent.class, modules = {ServiceModule.class})
public interface ServiceComponent {
//    void inject(UpdateService service);
//    void inject(ResourceService service);
}
