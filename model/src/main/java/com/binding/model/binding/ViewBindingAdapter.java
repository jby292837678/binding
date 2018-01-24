package com.binding.model.binding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsSpinner;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterViewAnimator;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SpinnerAdapter;

import com.binding.model.adapter.IModelAdapter;
import com.binding.model.model.ViewParse;
import com.binding.model.model.inter.Measure;

import java.util.Collection;

/**
 * Created by arvin on 2018/1/17.
 */

public class ViewBindingAdapter {

    @BindingAdapter("alpha")
    public static void setAlpha(View view,float alpha){
        if(alpha>=0&&alpha<=1)view.setAlpha(alpha);
    }

    @BindingAdapter({"adapter"})
    public static void setAdapter(View view, IModelAdapter adapter) {
        if (adapter == null) return;
        if (view instanceof RecyclerView && adapter instanceof RecyclerView.Adapter) {
            ((RecyclerView) view).setAdapter((RecyclerView.Adapter) adapter);
        } else if (view instanceof ViewPager && adapter instanceof PagerAdapter) {
            ((ViewPager) view).setAdapter((PagerAdapter) adapter);
        } else if (view instanceof AbsListView && adapter instanceof ListAdapter) {
            ((ListView) view).setAdapter((ListAdapter) adapter);
        } else if (view instanceof AbsSpinner && adapter instanceof SpinnerAdapter) {
            ((AbsSpinner) view).setAdapter((SpinnerAdapter) adapter);
        } else if (view instanceof AdapterViewAnimator && adapter instanceof Adapter) {
            ((AdapterViewAnimator) view).setAdapter((Adapter) adapter);
        } else if (view instanceof AdapterView && adapter instanceof Adapter) {
            ((AdapterView) view).setAdapter((Adapter) adapter);
        }
    }



    @BindingAdapter("params")
    public static void setLayoutParams(View view, ViewGroup.LayoutParams params) {
        view.setLayoutParams(params);
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
}
