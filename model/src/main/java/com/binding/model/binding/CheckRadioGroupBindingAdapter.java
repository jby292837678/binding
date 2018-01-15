package com.binding.model.binding;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.databinding.adapters.ListenerUtil;
import android.widget.RadioGroup;

import com.binding.model.R;
import com.binding.model.view.radio.CheckRadioGroup;

/**
 * Created by arvin on 2018/1/15.
 */
@InverseBindingMethods({
        @InverseBindingMethod(type = CheckRadioGroup.class, attribute = "checkPosition", event = "checkPositionAttrChanged", method = "getCheckedPosition")
})
public class CheckRadioGroupBindingAdapter {
    @BindingAdapter("checkPosition")
    public static void checkPosition(CheckRadioGroup radioGroup, int checkPosition){
        if(radioGroup.getCheckedPosition() != checkPosition){
            radioGroup.checkPosition(checkPosition);
        }
    }

    @BindingAdapter(value = {"checkedChange","checkPositionAttrChanged"},requireAll = false)
    public static void addOnCheckedChangeListener(CheckRadioGroup checkRadioGroup, CheckRadioGroup.OnCheckedChangeListener listener,
                                                  InverseBindingListener checkPositionAttrChanged){
        RadioGroup.OnCheckedChangeListener newValue = (group, checkedId) -> {
            listener.onCheckedChanged(group,checkedId);
            checkPositionAttrChanged.onChange();
        };
        RadioGroup.OnCheckedChangeListener oldValue = ListenerUtil.trackListener(checkRadioGroup, newValue, R.id.radio_group_layout);
        if(oldValue!=null)checkRadioGroup.setOnCheckedChangeListener(null);
        checkRadioGroup.setOnCheckedChangeListener(newValue);
    }

}

//@InverseBindingMethods({@InverseBindingMethod(type = ViewPager.class, attribute = "current", event = "currentAttrChanged", method = "getCurrentItem")})
//public class ViewPagerBindingAdapter {
//    @BindingAdapter({"current"})
//    public static void setCurrentItem(ViewPager view, int position) {
//        if (view.getCurrentItem() != position) view.setCurrentItem(position);
//    }
//
//
//    @BindingAdapter(value = {"change","currentAttrChanged"},requireAll =  false)
//    public static void addOnPageChangeListener(ViewPager pager,ViewPager.OnPageChangeListener listener,InverseBindingListener currentAttrChanged){
//        ViewPager.OnPageChangeListener newValue = new ViewPager.OnPageChangeListener() {
//            @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
//            @Override public void onPageScrollStateChanged(int state) {}
//            @Override public void onPageSelected(int position) {
//                if (currentAttrChanged != null) currentAttrChanged.onChange();
//                if (listener != null) listener.onPageSelected(position);
//            }
//        };
//        ViewPager.OnPageChangeListener oldValue = ListenerUtil.trackListener(pager, newValue, R.id.view_pager_layout);
//        pager.removeOnPageChangeListener(oldValue);
//        pager.addOnPageChangeListener(newValue);
//    }
//}