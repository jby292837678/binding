package com.binding.demo.ui.bottom;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.binding.demo.R;
import com.binding.demo.databinding.ActivityBottomSheetBinding;
import com.binding.demo.inject.qualifier.manager.ActivityFragmentManager;
import com.binding.demo.ui.message.MessageEntity;
import com.binding.model.adapter.AdapterType;
import com.binding.model.adapter.pager.FragmentStateAdapter;
import com.binding.model.layout.pager.PagerModel;
import com.binding.model.model.ModelView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by arvin on 2018/1/23.
 */

@ModelView(R.layout.activity_bottom_sheet)
public class BottomSheetModel extends PagerModel<BottomSheetActivity,ActivityBottomSheetBinding,MessageEntity> {
    @Inject BottomSheetModel(@ActivityFragmentManager FragmentManager manager) {super(new FragmentStateAdapter<>(manager));}
    private final List<MessageEntity> list = new ArrayList<>();
    @Override
    public void attachView(Bundle savedInstanceState, BottomSheetActivity bottomSheetActivity) {
        super.attachView(savedInstanceState, bottomSheetActivity);
        if(list.isEmpty()){
            list.add(new MessageEntity());
            list.add(new MessageEntity());
        }
        getAdapter().setList(0,list, AdapterType.refresh);


    }
}

/*
* behavior.setBottomSheetCallback(new BottomSheet.BottomSheetCallback() {
    public boolean hasRequest;

    @Override
    public void onStateChanged(@NonNull View bottomSheet, int newState) {
    }

    @Override
    public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        if (!hasRequest && behavior.getPeekHeight() == 0 && slideOffset > 0) {
            hasRequest = true;
            updateOffsets(bottomSheet);
        }
    }
});*/
