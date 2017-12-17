package com.binding.demo.ui.home.page;

import com.binding.demo.R;
import com.binding.demo.databinding.FragmentHomePageBinding;
import com.binding.model.adapter.recycler.RecyclerSelectAdapter;
import com.binding.model.layout.recycler.RecyclerModel;
import com.binding.model.model.ModelView;

/**
 * Created by arvin on 2017/12/8.
 */

@ModelView(R.layout.fragment_home_page)
public class HomePageModel extends RecyclerModel<HomePageFragment,FragmentHomePageBinding,HomePageEntity>{
    public HomePageModel() {
        super(new RecyclerSelectAdapter<>());
    }
}
