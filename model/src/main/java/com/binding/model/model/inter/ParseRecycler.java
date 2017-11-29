package com.binding.model.model.inter;

/**
 * Created by apple on 2017/7/28.
 */

public interface ParseRecycler extends Parse{
    boolean areItemsTheSame(Parse parseRecycler);
    boolean areContentsTheSame(Parse parseRecycler);
    void check(boolean check);
}
