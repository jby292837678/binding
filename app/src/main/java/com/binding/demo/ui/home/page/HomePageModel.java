package com.binding.demo.ui.home.page;

import android.os.Bundle;
import android.view.View;

import com.binding.demo.R;
import com.binding.demo.databinding.FragmentHomePageBinding;
import com.binding.model.adapter.AdapterType;
import com.binding.model.adapter.IEventAdapter;
import com.binding.model.adapter.list.ListAdapter;
import com.binding.model.adapter.recycler.RecyclerSelectAdapter;
import com.binding.model.layout.recycler.RecyclerModel;
import com.binding.model.model.ModelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arvin on 2017/12/8.
 */

@ModelView(R.layout.fragment_home_page)
public class HomePageModel extends RecyclerModel<HomePageFragment,FragmentHomePageBinding,HomePageEntity>{
    public HomePageModel() {
        super(new RecyclerSelectAdapter<>());
    }

    @Override
    public void attachView(Bundle savedInstanceState, HomePageFragment homePageFragment) {
        super.attachView(savedInstanceState, homePageFragment);
        getDataBinding().layoutRecycler.setVm(this);
        setEnable(false);
        setPageFlag(false);
        List<HomePageEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) list.add(new HomePageEntity("name:"+i,i));
        getAdapter().setList(0,list,AdapterType.add);
    }
}
