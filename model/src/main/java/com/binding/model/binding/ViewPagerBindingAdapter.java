package com.binding.model.binding;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.InverseBindingMethod;
import androidx.databinding.InverseBindingMethods;
import androidx.databinding.adapters.ListenerUtil;
import androidx.viewpager.widget.ViewPager;

import com.binding.model.R;

/**
 * Created by arvin on 2018/1/15.
 */
@InverseBindingMethods({@InverseBindingMethod(type = ViewPager.class, attribute = "position", event = "positionAttrChanged", method = "getCurrentItem")})
public class ViewPagerBindingAdapter {
    @BindingAdapter("position")
    public static void setCurrentItem(ViewPager view, int position) {
        if (view.getCurrentItem() != position) view.setCurrentItem(position);
    }

    @BindingAdapter(value = {"pageChange","positionAttrChanged"},requireAll =  false)
    public static void addOnPageChangeListener(ViewPager pager,ViewPager.OnPageChangeListener listener,InverseBindingListener positionAttrChanged){
        ViewPager.OnPageChangeListener newValue = new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {if(listener!=null)listener.onPageScrolled(position, positionOffset, positionOffsetPixels);}
            @Override public void onPageScrollStateChanged(int state) {if(listener!=null)listener.onPageScrollStateChanged(state);}
            @Override public void onPageSelected(int position) {
                if (positionAttrChanged != null) positionAttrChanged.onChange();
                if (listener != null) listener.onPageSelected(position);
            }
        };
        ViewPager.OnPageChangeListener oldValue = ListenerUtil.trackListener(pager, newValue, R.id.view_pager_layout);
        pager.removeOnPageChangeListener(oldValue);
        pager.addOnPageChangeListener(newValue);
    }
}