package binding.com.demo.ui.home;

import android.view.ViewGroup;

import com.binding.model.model.ViewParse;
import com.binding.model.model.inter.Item;

import binding.com.demo.base.cycle.BaseFragment;
import binding.com.demo.ui.home.page.HomePageFragment;

public class HomeEntity extends ViewParse implements Item<BaseFragment> {
    private BaseFragment fragment;

    @Override
    public BaseFragment getItem(int position, ViewGroup container) {
        if(fragment == null)
            fragment = new HomePageFragment();
        return fragment;
    }
}
