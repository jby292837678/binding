package com.binding.demo.ui.bottom.fragment;

import android.view.ViewGroup;

import com.binding.demo.ui.bottom.select.SelectSheetFragment;
import com.binding.model.model.ViewParse;
import com.binding.model.model.inter.Item;

/**
 * Created by arvin on 2018/1/23.
 */

public class FullSheetDialogEntity extends ViewParse implements Item<SelectSheetFragment>{
    private SelectSheetFragment fragment;
    @Override
    public SelectSheetFragment getItem(int position, ViewGroup container) {
        if(fragment == null)fragment = new SelectSheetFragment() ;
        return fragment;
    }
}
