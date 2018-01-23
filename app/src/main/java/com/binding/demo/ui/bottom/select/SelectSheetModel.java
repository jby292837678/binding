package com.binding.demo.ui.bottom.select;

import com.binding.demo.R;
import com.binding.demo.databinding.FragmentBottomSelectBinding;
import com.binding.model.model.ModelView;
import com.binding.model.model.ViewModel;

import javax.inject.Inject;

/**
 * Created by arvin on 2018/1/23.
 */

@ModelView(R.layout.fragment_bottom_select)
public class SelectSheetModel extends ViewModel<SelectSheetFragment,FragmentBottomSelectBinding>{
    @Inject SelectSheetModel() {
    }
}
