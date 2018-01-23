package com.binding.demo.ui.bottom;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewParent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.binding.demo.R;
import com.binding.demo.base.cycle.BaseActivity;
import com.binding.demo.inject.component.ActivityComponent;
import com.binding.demo.ui.bottom.fragment.FullSheetDialogFragment;

/**
 * Created by arvin on 2018/1/23.
 */

@Route(path = ActivityComponent.Router.bottom_sheet)
public class BottomSheetActivity extends BaseActivity<BottomSheetModel> {
    public boolean hasRequest;
    private BottomSheetBehavior<View> behavior;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View bottomSheet = findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                //这里是bottomSheet 状态的改变
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //这里是拖拽中的回调，根据slideOffset可以做一些动画
//
//                if (!hasRequest && behavior.getPeekHeight() == 0 && slideOffset > 0) {
//                    hasRequest = true;
//                    updateOffsets(bottomSheet);
//                }
            }

        });
    }
//
//    private void updateOffsets(View view) {
//
//        // Manually invalidate the view and parent to make sure we get drawn pre-M
//        if (Build.VERSION.SDK_INT < 23) {
//            tickleInvalidationFlag(view);
//            final ViewParent vp = view.getParent();
//            if (vp instanceof View) {
//                tickleInvalidationFlag((View) vp);
//            }
//        }
//    }
//
//    private static void tickleInvalidationFlag(View view) {
//        final float y = ViewCompat.getTranslationY(view);
//        ViewCompat.setTranslationY(view, y + 1);
//        ViewCompat.setTranslationY(view, y);
//    }



    public void doclick(View v) {
        switch (v.getId()) {
            case R.id.button0:
                if(behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }else {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                break;
            case R.id.button2:
                new FullSheetDialogFragment().show(getSupportFragmentManager(), "dialog");
                break;
        }
    }
}
