package com.binding.model.cycle;

import androidx.appcompat.widget.Toolbar;

/**
 * Created by pc on 2017/8/24.
 */

public interface ActivityContainer<T> extends CycleContainer<T> {
    Toolbar getToolbar();
}
