package com.binding.demo.ui.home.page;

import android.os.Bundle;

import com.binding.demo.R;
import com.binding.demo.databinding.FragmentHomePageBinding;
import com.binding.model.App;
import com.binding.model.adapter.recycler.RecyclerSelectAdapter;
import com.binding.model.layout.recycler.RecyclerModel;
import com.binding.model.model.ModelView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by arvin on 2017/12/8.
 */

@ModelView(R.layout.fragment_home_page)
public class HomePageModel extends RecyclerModel<HomePageFragment, FragmentHomePageBinding, HomePageEntity> {
    @Inject
    HomePageModel() {
        super(new RecyclerSelectAdapter<>());
    }

    @Override
    public void attachView(Bundle savedInstanceState, HomePageFragment homePageFragment) {
        super.attachView(savedInstanceState, homePageFragment);
        getDataBinding().layoutRecycler.setVm(this);
        App.pageWay = true;
        setRcHttp((offset1, refresh) ->
                {
                    List<HomePageEntity> list = new ArrayList<>();
                    for (int i = 0; i < 18; i++) list.add(new HomePageEntity("name:" + i, i));
                    return Observable.fromIterable(list).toList().toObservable();
                }
        );
    }
}