package com.binding.demo.ui.home;

import android.view.ViewGroup;

import com.binding.demo.base.cycle.BaseFragment;
import com.binding.library.model.inter.Item;

/**
 * Created by arvin on 2017/11/27.
 */

public class HomeEntity implements Item<BaseFragment> {
    private BaseFragment fragment;

    @Override
    public BaseFragment getItem(int position, ViewGroup container) {
        if(fragment == null){
            switch (position){
            }
        }
        return fragment;
    }
}
