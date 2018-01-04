package com.binding.model.bit;

import android.view.View;

/**
 * Created by arvin on 2017/11/28.
 */

public class Bit {

    /**
     * @param total the count of judge
     * */
    public static View bit(int variable, JudgeBit<Boolean,View> judgeBit, int total){
        View view = null;
        for (int i = 0; i < total; i++) {
            view = judgeBit.judge(i,(variable&1) == 1,view);
            variable = variable>>>1;
        }
        return view;
    }

    public static int bitInteger(int variable, JudgeBit<Boolean,Integer> judgeBit, int total){
        int p = 0;
        for (int i = 0; i < total; i++) {
            p = judgeBit.judge(i,(variable&1) == 1,total-i);
            variable = variable>>>1;
        }
        return p;
    }
}
