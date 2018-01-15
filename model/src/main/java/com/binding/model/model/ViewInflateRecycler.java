package com.binding.model.model;

import android.databinding.ViewDataBinding;

import com.binding.model.adapter.AdapterHandle;
import com.binding.model.adapter.AdapterType;
import com.binding.model.model.inter.Parse;
import com.binding.model.model.inter.Recycler;

/**
 * Created by pc on 2017/9/26.
 */

public abstract class ViewInflateRecycler<Binding extends ViewDataBinding> extends ViewInflate<Binding> implements Recycler<Binding> {

    @Override
    public boolean areContentsTheSame(Parse parseRecycler) {
        return this.equals(parseRecycler);
    }

    @Override
    public void check(int check) {
        check((check&1) == 1);
    }

    public void check(boolean checked){

    }

    @Override
    public Object key() {
        return this;
    }

}
