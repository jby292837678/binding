package com.binding.model.binding;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binding.model.adapter.IEventAdapter;
import com.binding.model.model.ViewParse;
import com.binding.model.model.inter.Inflate;
import com.binding.model.model.inter.Measure;

import java.util.Collection;
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
        for (Inflate inflate : inflates){
            if(inflate instanceof Measure){
                View view = inflate.attachView(group.getContext(), group, false, null).getRoot();
                ViewGroup.LayoutParams params = ((Measure) inflate).measure(view,group);
                group.addView(view,params);
            }else
                inflate.attachView(group.getContext(), group, true, null);
        }
    }


    @BindingAdapter("parses")
    public static void parses(ViewGroup viewGroup, Collection<? extends ViewParse> parses) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        for (ViewParse parse : parses) {
            if (parse instanceof Measure) {
                ViewDataBinding binding = DataBindingUtil.inflate(inflater, parse.getLayoutId(), viewGroup, true);
                binding.getRoot().setLayoutParams(((Measure) parse).measure(binding.getRoot(), viewGroup));
                binding.setVariable(parse.getVariableName(), parse);
            } else {
                ViewDataBinding binding = DataBindingUtil.inflate(inflater, parse.getLayoutId(), viewGroup, true);
                binding.setVariable(parse.getVariableName(), parse);
            }
        }
    }


    @BindingAdapter(value = {"inflates","eventAdapter"})
    public static void addInflates(ViewGroup group, List<? extends Inflate> inflates, IEventAdapter eventAdapter) {
        group.removeAllViews();
        if (inflates == null || inflates.isEmpty()) return;
        for (Inflate inflate : inflates){
            inflate.setIEventAdapter(eventAdapter);
            if(inflate instanceof Measure){
                View view = inflate.attachView(group.getContext(), group, false, null).getRoot();
                ViewGroup.LayoutParams params = ((Measure) inflate).measure(view,group);
                group.addView(view,params);
            }else
                inflate.attachView(group.getContext(), group, true, null);
        }
    }

}
