package com.binding.model.view.edit;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.binding.model.util.BaseUtil;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：9:31
 * modify developer：  admin
 * modify time：9:31
 * modify remark：
 *
 * @version 2.0
 */


public class TextValidWatcher implements TextWatcher {

    private ValidListener listener;
    private TextView textView;

    public TextValidWatcher(ValidListener listener, TextView textView) {
        this.listener = listener;
        this.textView = textView;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        BaseUtil.setError(textView,listener.isValid(s));
    }


    public interface ValidListener{
        String isValid(Editable s);
    }
}
