package binding.com.demo.ui.home.page;

import android.os.Bundle;

import com.binding.model.layout.recycler.RecyclerModel;
import com.binding.model.model.ModelView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import binding.com.demo.R;
import binding.com.demo.databinding.FragmentHomePageBinding;
import binding.com.demo.ui.home.HomeEntity;
import io.reactivex.Observable;

@ModelView(R.layout.fragment_home_page)
public class HomePageModel extends RecyclerModel<HomePageFragment,FragmentHomePageBinding,HomePageEntity>{
    @Inject HomePageModel() {
    }

    @Override
    public void attachView(Bundle savedInstanceState, HomePageFragment homePageFragment) {
        super.attachView(savedInstanceState, homePageFragment);
        getDataBinding().layoutRecycler.setVm(this);
        List<HomePageEntity> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(new HomePageEntity());
        }
        setRcHttp((offset1, refresh) -> Observable.fromIterable(list).toList().toObservable());
    }
}
