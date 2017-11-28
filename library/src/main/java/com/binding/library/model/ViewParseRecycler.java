package com.binding.library.model;

import com.binding.library.model.inter.Parse;
import com.binding.library.model.inter.ParseRecycler;

/**
 * Created by apple on 2017/7/29.
 */

public abstract class ViewParseRecycler extends ViewParse implements ParseRecycler {
    @Override
    public boolean areContentsTheSame(Parse parse) {
        return this.equals(parse);
    }

    @Override
    public void check(boolean check) {

    }
}
