package com.binding.library.model;

import android.databinding.ViewDataBinding;
import android.view.ViewGroup;

import com.binding.library.model.inter.Item;

/**
 * Created by pc on 2017/9/28.
 */

public class ViewItemInflate<Binding extends ViewDataBinding> extends ViewInflate<Binding> implements Item<Integer> {
    @Override
    public Integer getItem(int position, ViewGroup container) {
        return 0;
    }
}
