package binding.com.demo.ui.home;

import com.alibaba.android.arouter.facade.annotation.Route;

import binding.com.demo.base.cycle.BaseActivity;
import binding.com.demo.inject.component.ActivityComponent;

@Route(path = ActivityComponent.Router.home)
public class HomeActivity extends BaseActivity<HomeModel> {

}
