package com.binding.model.binding;

import android.databinding.BindingAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.binding.model.model.inter.Inflate;

import java.util.List;

/**
 * Created by arvin on 2018/1/17.
 */
@SuppressWarnings("unchecked")
public class ViewGroupBindingAdapter {

    @BindingAdapter("inflate")
    public static void addInflate(ViewGroup viewGroup, Inflate inflate){
        viewGroup.removeAllViews();
        inflate.attachView(viewGroup.getContext(),viewGroup,true,null);
    }

    @BindingAdapter("inflates")
    public static void addInflates(ViewGroup group, List<? extends Inflate> inflates) {
        group.removeAllViews();
        if (inflates == null || inflates.isEmpty()) return;
        for (Inflate inflate : inflates)
            inflate.attachView(group.getContext(), group, true, null).getRoot();
    }

}
