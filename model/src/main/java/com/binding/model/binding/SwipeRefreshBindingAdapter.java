package com.binding.model.binding;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.databinding.adapters.ListenerUtil;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.RadioGroup;
import android.widget.SpinnerAdapter;

import com.binding.model.R;
import com.binding.model.adapter.IModelAdapter;
import com.binding.model.model.inter.Inflate;


/**
 * description：
 * create developer： admin
 * create time：14:25
 * modify developer：  admin
 * modify time：14:25
 * modify remark：
 *
 * @version 2.0
 */

@InverseBindingMethods({
        @InverseBindingMethod(type = SwipeRefreshLayout.class, attribute = "refreshing", event = "refreshingAttrChanged", method = "isRefreshing")
})
public class SwipeRefreshBindingAdapter {
    @BindingAdapter({"refreshing"})
    public static void setRefreshing(SwipeRefreshLayout view, boolean refreshing) {
        if (refreshing != view.isRefreshing()) {
            view.setRefreshing(refreshing);
        }
    }

    @BindingAdapter(value = {"onRefreshListener", "refreshingAttrChanged"}, requireAll = false)
    public static void setOnRefreshListener(SwipeRefreshLayout view, SwipeRefreshLayout.OnRefreshListener listener, InverseBindingListener refreshingAttrChanged) {
        SwipeRefreshLayout.OnRefreshListener newValue = () -> {
            if (refreshingAttrChanged != null) refreshingAttrChanged.onChange();
            if (listener != null) listener.onRefresh();
        };
        SwipeRefreshLayout.OnRefreshListener oldValue = ListenerUtil.trackListener(view, newValue, R.id.swipe_refresh_layout);
        if (oldValue != null) view.setOnRefreshListener(null);
        view.setOnRefreshListener(newValue);
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



    @BindingAdapter({"layout_manager"})
    public static void setLayoutManager(RecyclerView view, RecyclerView.LayoutManager layoutManager) {
        if (layoutManager != null) view.setLayoutManager(layoutManager);
    }


    @BindingAdapter({"scroll_listener"})
    public static void setOnScroll(RecyclerView view, RecyclerView.OnScrollListener listener) {
        if (listener != null) view.addOnScrollListener(listener);
    }

    @BindingAdapter("checkedPosition")
    public static void checkPosition(RadioGroup group, int position) {
        if (position < group.getChildCount()) group.check(group.getChildAt(position).getId());
    }

    @BindingAdapter("checked")
    public static void addCheckedListener(RadioGroup group, RadioGroup.OnCheckedChangeListener listener) {
        if (listener != null) group.setOnCheckedChangeListener(listener);
    }

    @BindingAdapter("pager_change")
    public static void addOnPagerChange(ViewPager pager, ViewPager.OnPageChangeListener listener) {
        pager.addOnPageChangeListener(listener);
    }

    @BindingAdapter("currentItem")
    public static void setCurrentItem(ViewPager pager, int currentItem) {
        pager.setCurrentItem(currentItem);
    }
    @BindingAdapter("inflate")
    public static void addInflate(ViewGroup viewGroup, Inflate inflate){
        viewGroup.removeAllViews();
        inflate.attachView(viewGroup.getContext(),viewGroup,true,null);
    }
}
