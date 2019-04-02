package com.binding.model.model;

import androidx.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;

import com.binding.model.cycle.Container;
import com.binding.model.model.inter.Item;

/**
 * Created by pc on 2017/9/5.
 */

public class ViewItemModel<T extends Container,Binding extends ViewDataBinding> extends ViewModel<T,Binding> implements Item<View> {
    private transient int modelIndex;



    @Override
    public View getItem(int position, ViewGroup container) {
        modelIndex = position;
        if(getDataBinding() == null)attachView(container.getContext(),container,false,null);
        return getDataBinding().getRoot();
    }

    @Override
    public int getModelIndex() {
        return modelIndex;
    }
}
