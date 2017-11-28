package com.binding.library.model.inter;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by pc on 2017/8/29.
 */

public interface Measure {
    ViewGroup.LayoutParams measure(View view, ViewGroup parent);
}
