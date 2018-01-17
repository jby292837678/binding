package com.binding.model.binding;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.support.v7.widget.RecyclerView;

/**
 * Created by arvin on 2018/1/16.
 */


@InverseBindingMethods({
        @InverseBindingMethod(type = RecyclerView.class, attribute = "", event = "positionAttrChanged", method = "")
})
public class RecyclerViewBindingAdapter {
    @BindingAdapter("position")
    public static void position(RecyclerView view,int position){

//        view.getposi
    }
}

//@InverseBindingMethods({
//        @InverseBindingMethod(type = SwipeRefreshLayout.class, attribute = "refreshing", event = "refreshingAttrChanged", method = "isRefreshing")
//})
//public class SwipeRefreshBindingAdapter {
//    @BindingAdapter({"refreshing"})
//    public static void setRefreshing(SwipeRefreshLayout view, boolean refreshing) {
//        if (refreshing != view.isRefreshing()) {
//            view.setRefreshing(refreshing);
//        }
//    }
//
//    @BindingAdapter(value = {"onRefreshListener", "refreshingAttrChanged"}, requireAll = false)
//    public static void setOnRefreshListener(SwipeRefreshLayout view, SwipeRefreshLayout.OnRefreshListener listener, InverseBindingListener refreshingAttrChanged) {
//        SwipeRefreshLayout.OnRefreshListener newValue = () -> {
//            if (refreshingAttrChanged != null) refreshingAttrChanged.onChange();
//            if (listener != null) listener.onRefresh();
//        };
//        SwipeRefreshLayout.OnRefreshListener oldValue = ListenerUtil.trackListener(view, newValue, R.id.swipe_refresh_layout);
//        if (oldValue != null) view.setOnRefreshListener(null);
//        view.setOnRefreshListener(newValue);
//    }