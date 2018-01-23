package com.binding.demo.ui.bottom.fragment;

import android.support.design.widget.BottomSheetBehavior;

import com.binding.demo.base.cycle.BaseSheetDialogFragment;


public class FullSheetDialogFragment extends BaseSheetDialogFragment<FullSheetDialogModel> {
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
//        View view = View.inflate(getContext(), R.layout.dialog_bottom_sheet, null);
//        dialog.setContentView(view);
//        behavior = BottomSheetBehavior.from((View) view.getParent());
//        return dialog;
//    }
//
    @Override
    public void onStart() {
        super.onStart();
        getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);//全屏展开
    }


//    public void doclick(View v)
//    {
//        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
//    }
}