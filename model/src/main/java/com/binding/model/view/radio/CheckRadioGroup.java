package com.binding.model.view.radio;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
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
        if(checkId == -1)return 0;
        return indexOfChild(findViewById(checkId));
    }

    public void checkPosition(int checkPosition) {
        View view = getChildAt(checkPosition);
        if(view == null)return;
        check(view.getId());
    }
}
