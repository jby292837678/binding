package com.binding.model.view.radio;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioGroup;

/**
 * Created by arvin on 2018/1/15.
 */

public class CheckRadioGroup extends RadioGroup {
    public CheckRadioGroup(Context context) {
        super(context);
    }

    public CheckRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getCheckedPosition(){
        int checkId = getCheckedRadioButtonId();
        return indexOfChild(findViewById(checkId));
    }

    public void checkPosition(int checkPosition) {
        check(getChildAt(checkPosition).getId());
    }

    @Override
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        super.setOnCheckedChangeListener(listener);

    }
}
