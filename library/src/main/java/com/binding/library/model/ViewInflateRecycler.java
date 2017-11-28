package com.binding.library.model;

import android.databinding.ViewDataBinding;

import com.binding.library.model.inter.Parse;
import com.binding.library.model.inter.ParseRecycler;

/**
 * Created by pc on 2017/9/26.
 */

public abstract class ViewInflateRecycler<Binding extends ViewDataBinding> extends ViewInflate<Binding> implements ParseRecycler {

    @Override
    public boolean areContentsTheSame(Parse parseRecycler) {
        return this.equals(parseRecycler);
    }

    @Override
    public void check(boolean check) {

    }
}
