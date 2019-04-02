package com.binding.model.view.composite;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.binding.model.R;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：11:15
 * modify developer：  admin
 * modify time：11:15
 * modify remark：
 *
 * @version 2.0
 */


public class CompositeArrayLayout<Entity> extends FrameLayout {
    public CompositeArrayLayout(@NonNull Context context) {
        this(context,null);
    }

    public CompositeArrayLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CompositeArrayLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context,attrs,defStyleAttr);
    }

    private void initParams(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater inflater = LayoutInflater.from(context);
        TypedArray typedArray =context.obtainStyledAttributes(attrs, R.styleable.CompositeArrayLayout);
        int layout = typedArray.getResourceId(R.styleable.CompositeArrayLayout_layout,0);
        int holder = typedArray.getResourceId(R.styleable.CompositeArrayLayout_holder,0);
        View view = inflater.inflate(layout,this);
        inflater.inflate(holder,(ViewGroup) view);
        typedArray.recycle();
    }
}
