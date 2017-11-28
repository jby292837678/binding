package com.binding.library.cycle;

import android.support.v7.widget.Toolbar;

/**
 * Created by pc on 2017/8/24.
 */

public interface ActivityContainer<T> extends CycleContainer<T> {
    Toolbar getToolbar();
}
