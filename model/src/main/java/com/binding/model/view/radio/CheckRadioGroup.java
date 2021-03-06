package com.binding.model.view.radio;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
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
        if(checkId == -1)return -1;
        return indexOfChild(findViewById(checkId));
    }

    public void checkPosition(int checkPosition) {
        if(checkPosition<0||checkPosition>=getChildCount()){
            this.clearCheck();
        }else{
            View view = getChildAt(checkPosition);
            if(view != null) check(view.getId());
        }
    }
}
