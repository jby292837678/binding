package binding.com.demo.ui.drag;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.binding.model.view.swipe.SwipeBackLayout;

import binding.com.demo.base.cycle.BaseActivity;
import binding.com.demo.inject.component.ActivityComponent;

@Route(path = ActivityComponent.Router.drag)
public class DragActivity extends BaseActivity<DragModel> {
    @Override
    protected int isSwipe() {
        return SwipeBackLayout.FROM_NO;
    }
}
