package com.binding.model.model;

import com.binding.model.model.inter.Parse;
import com.binding.model.model.inter.ParseRecycler;

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
