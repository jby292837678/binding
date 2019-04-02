package binding.com.demo.inject.module;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;

import binding.com.demo.inject.qualifier.context.FragmentContext;
import binding.com.demo.inject.qualifier.manager.ChildFragmentManager;
import binding.com.demo.inject.qualifier.manager.SupportFragmentManager;
import binding.com.demo.inject.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：13:38
 * modify developer：  admin
 * modify time：13:38
 * modify remark：
 *
 * @version 2.0
 */
@FragmentScope
@Module
public class FragmentModule {
    private final Fragment fragment;
    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @FragmentScope
    @Provides
    @FragmentContext
    Context provideContext() {
        return fragment.getActivity();
    }

    @FragmentScope
    @Provides
    @SupportFragmentManager
    FragmentManager provideManager() {
        return fragment.getFragmentManager();
    }

    @FragmentScope
    @Provides
    @ChildFragmentManager
    FragmentManager provideChildManager() {
        return fragment.getChildFragmentManager();
    }

    @FragmentScope
    @Provides
    LayoutInflater provideLayoutInflater(@FragmentContext Context context){
        return LayoutInflater.from(context);
    }

    @FragmentScope
    @Provides
    DisplayMetrics provideDisplayMetrics(@FragmentContext Context context){
        return context.getResources().getDisplayMetrics();
    }


}
