package com.binding.model.model.inter;

import android.databinding.ViewDataBinding;

import com.binding.model.adapter.AdapterHandle;

/**
 * Created by apple on 2017/7/28.
 */

public interface Recycler<Binding extends ViewDataBinding> extends GridInflate<Binding>,Event{
    Object key();
    boolean areContentsTheSame(Parse parseRecycler);
    void check(boolean check);
    int getCheckType();

    /**
     *          0       1       2       3
     * push     false   true   false    true
     * takeBack false   false  true     true
     * */
    int checkWay();
}
