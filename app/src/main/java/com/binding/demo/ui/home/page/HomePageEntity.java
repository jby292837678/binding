package com.binding.demo.ui.home.page;

import com.binding.model.model.ViewInflateRecycler;
import com.binding.model.model.inter.Parse;

/**
 * Created by arvin on 2017/12/8.
 */

public class HomePageEntity extends ViewInflateRecycler {
    @Override
    public boolean areItemsTheSame(Parse parseRecycler) {
        return false;
    }
}
