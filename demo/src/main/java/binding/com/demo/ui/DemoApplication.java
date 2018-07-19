package binding.com.demo.ui;

import android.app.Application;

import com.binding.model.App;

import binding.com.demo.BR;
import binding.com.demo.inject.component.AppComponent;
import binding.com.demo.inject.component.DaggerAppComponent;
import binding.com.demo.inject.module.AppModule;

public class DemoApplication extends Application {
    private static DemoApplication application;
    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        App.pageWay = true;
        application = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        App.getInstance().init(this,true, BR.vm);
    }

}
