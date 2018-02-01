package com.binding.model.model;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.ViewGroup;

import com.binding.model.App;
import com.binding.model.R;
import com.binding.model.adapter.AdapterHandle;
import com.binding.model.adapter.AdapterType;
import com.binding.model.model.inter.Parse;
import com.binding.model.model.inter.Recycler;

import timber.log.Timber;

/**
 * Created by pc on 2017/9/26.
 */

public class ViewInflateRecycler<Binding extends ViewDataBinding> extends ViewInflate<Binding> implements Recycler<Binding> {
    private transient int holder_position=-1;

    @Override
    public Binding attachView(Context context, ViewGroup co, boolean attachToParent, Binding binding) {
        if (co != null) {
            Object o = co.getTag(R.id.holder_position);
            if (o instanceof Integer) this.holder_position = (int) o;
        }
        return super.attachView(context, co, attachToParent, binding);
    }


    @Override
    public boolean areContentsTheSame(Parse parseRecycler) {
        return this.equals(parseRecycler);
    }

    @Override
    public int checkWay() {
        return 3;
    }

    @Override
    public void check(boolean checked) {
    }

    @Override
    public Object key() {
        return this;
    }

    @Override
    public int getSpanSize() {
        return 1;
    }

    @Override
    public int getCheckType() {
        return 0;
    }

    public int getHolder_position() {
        return holder_position;
    }
}
