package com.binding.model.model.inter;

import android.databinding.ViewDataBinding;

import com.binding.model.adapter.AdapterHandle;

/**
 * Created by apple on 2017/7/28.
 */

public interface Recycler<Binding extends ViewDataBinding> extends Inflate<Binding>,Event{
    Object key();
    boolean areContentsTheSame(Parse parseRecycler);
    void check(int check);

}
