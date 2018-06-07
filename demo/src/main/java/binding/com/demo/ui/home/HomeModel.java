package binding.com.demo.ui.home;



import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.binding.model.adapter.pager.FragmentAdapter;
import com.binding.model.layout.pager.PagerModel;
import com.binding.model.model.ModelView;
import com.binding.model.model.ViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import binding.com.demo.R;
import binding.com.demo.base.arouter.ArouterUtil;
import binding.com.demo.databinding.ActivityHomeBinding;
import binding.com.demo.inject.qualifier.manager.ActivityFragmentManager;
import io.reactivex.Observable;

import static binding.com.demo.inject.component.ActivityComponent.Router.address;

@ModelView(R.layout.activity_home)
public class HomeModel extends PagerModel<HomeActivity,ActivityHomeBinding,HomeEntity> {
    @Inject HomeModel(@ActivityFragmentManager FragmentManager fragmentManager) {
        super(new FragmentAdapter<>(fragmentManager));
    }

    @Override
    public void attachView(Bundle savedInstanceState, HomeActivity homeActivity) {
        super.attachView(savedInstanceState, homeActivity);
        List<HomeEntity> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(new HomeEntity());
        }
        setRcHttp((offset1, refresh) -> Observable.fromIterable(list).toList().toObservable());
        currentItem.set(0);
    }

    @Override
    public void onRightClick(View view) {
        super.onRightClick(view);
        ArouterUtil.navigation(address);
    }
}
