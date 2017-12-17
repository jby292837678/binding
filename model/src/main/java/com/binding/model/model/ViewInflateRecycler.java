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
    public void check(boolean check) {

    }
    //
//    @Override
//    public void respond(@AdapterHandle int type, int status, Object... args) {
//        switch (type){
//            case AdapterType.select:check(status == 1);break;
//        }
//    }
}
