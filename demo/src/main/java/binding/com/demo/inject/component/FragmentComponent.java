package binding.com.demo.inject.component;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import binding.com.demo.base.cycle.BaseSheetDialogFragment;
import binding.com.demo.inject.module.FragmentModule;
import binding.com.demo.inject.scope.FragmentScope;

import binding.com.demo.ui.home.page.HomePageFragment;
import dagger.Component;


/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：11:29
 * modify developer：  admin
 * modify time：11:29
 * modify remark：
 *
 * @version 2.0
 */

@FragmentScope
@Component(dependencies = AppComponent.class,modules={FragmentModule.class})
public interface FragmentComponent {
    void inject(HomePageFragment fragment);
}
