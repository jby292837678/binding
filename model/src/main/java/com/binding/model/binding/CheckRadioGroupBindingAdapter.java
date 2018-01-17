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
        @InverseBindingMethod(type = CheckRadioGroup.class, attribute = "position", event = "positionAttrChanged", method = "getCheckedPosition")
})
public class CheckRadioGroupBindingAdapter {
    @BindingAdapter("position")
    public static void checkPosition(CheckRadioGroup radioGroup, int position){
        if(radioGroup.getCheckedPosition() != position){
            radioGroup.checkPosition(position);
        }
    }

    @BindingAdapter(value = {"checkedChange","positionAttrChanged"},requireAll = false)
    public static void addOnCheckedChangeListener(CheckRadioGroup checkRadioGroup, CheckRadioGroup.OnCheckedChangeListener listener,
                                                  InverseBindingListener positionAttrChanged){
        RadioGroup.OnCheckedChangeListener newValue = (group, checkedId) -> {
            if(listener!=null)listener.onCheckedChanged(group,checkedId);
            if(positionAttrChanged!=null)positionAttrChanged.onChange();
        };
        RadioGroup.OnCheckedChangeListener oldValue = ListenerUtil.trackListener(checkRadioGroup, newValue, R.id.radio_group_layout);
        if(oldValue!=null)checkRadioGroup.setOnCheckedChangeListener(null);
        checkRadioGroup.setOnCheckedChangeListener(newValue);
    }
}



