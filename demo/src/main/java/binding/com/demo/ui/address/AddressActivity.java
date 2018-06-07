package binding.com.demo.ui.address;

import com.alibaba.android.arouter.facade.annotation.Route;

import binding.com.demo.base.cycle.BaseActivity;
import binding.com.demo.inject.component.ActivityComponent;

@Route(path = ActivityComponent.Router.address)
public class AddressActivity extends BaseActivity<AddressModel> {
}
