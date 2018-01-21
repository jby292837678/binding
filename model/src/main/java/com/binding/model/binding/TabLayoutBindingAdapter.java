package com.binding.model.binding;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.databinding.adapters.ListenerUtil;
import android.support.design.widget.TabLayout;

import com.binding.model.R;
import com.binding.model.util.ReflectUtil;

/**
 * Created by arvin on 2018/1/17.
 */

@InverseBindingMethods({
        @InverseBindingMethod(type = TabLayout.class, attribute = "position", event = "positionAttrChanged", method = "getSelectedTabPosition")
})
public class TabLayoutBindingAdapter {

    @BindingAdapter("position")
    public static void setScrollPosition(TabLayout layout,int position){
        int current = layout.getSelectedTabPosition();
        if(current == position||position<0)return;
//        layout.selectTab(tab);
        TabLayout.Tab tab =layout.getTabAt(position);
        ReflectUtil.invoke("selectTab",layout,tab);
        layout.setScrollPosition(position, 0f, true);
    }

    @BindingAdapter(value = {"tab_selected","positionAttrChanged"},requireAll = false)
    public static void addOnTabSelectedListener(TabLayout layout,TabLayout.OnTabSelectedListener listener,InverseBindingListener positionAttrChanged){
        TabLayout.OnTabSelectedListener newValue =  new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) {
                if(listener !=null)listener.onTabSelected(tab);
                if(positionAttrChanged!=null)positionAttrChanged.onChange();
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {if(listener !=null)listener.onTabUnselected(tab);}
            @Override public void onTabReselected(TabLayout.Tab tab) {if(listener !=null)listener.onTabReselected(tab);}
        };
        TabLayout.OnTabSelectedListener oldValue = ListenerUtil.trackListener(layout,newValue, R.id.tab_layout);
        if(oldValue!=null)layout.removeOnTabSelectedListener(oldValue);
        layout.addOnTabSelectedListener(newValue);
    }
}
