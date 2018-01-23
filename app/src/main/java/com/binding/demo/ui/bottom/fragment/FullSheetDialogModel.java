package com.binding.demo.ui.bottom.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.binding.demo.R;
import com.binding.demo.databinding.DialogBottomSheetBinding;
import com.binding.demo.inject.qualifier.manager.ChildFragmentManager;
import com.binding.model.adapter.pager.FragmentStateAdapter;
import com.binding.model.layout.pager.PagerModel;
import com.binding.model.model.ModelView;

import javax.inject.Inject;

/**
 * Created by arvin on 2018/1/23.
 */
@ModelView(R.layout.dialog_bottom_sheet)
public class FullSheetDialogModel extends PagerModel<FullSheetDialogFragment,DialogBottomSheetBinding,FullSheetDialogEntity> {
    @Inject FullSheetDialogModel(@ChildFragmentManager FragmentManager fragmentManager) {
        super(new FragmentStateAdapter<>(fragmentManager));
    }

    @Override
    public void attachView(Bundle savedInstanceState, FullSheetDialogFragment fullSheetDialogFragment) {
        super.attachView(savedInstanceState, fullSheetDialogFragment);

    }

    private void setBehaviorState(int state){
        if(getT() == null||getT().getBehavior() == null)return;
        getT().getBehavior().setState(state);
    }
}
