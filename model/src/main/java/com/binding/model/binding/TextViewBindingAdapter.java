package com.binding.model.binding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

/**
 * Created by arvin on 2018/1/17.
 */

public class TextViewBindingAdapter {


    @BindingAdapter({"android:drawableBottom"})
    public static void setDrawableBottom(TextView view, int drawable) {
        android.databinding.adapters.TextViewBindingAdapter.setDrawableBottom(view, ContextCompat.getDrawable(view.getContext(), drawable));
    }

    @BindingAdapter({"android:drawableLeft"})
    public static void setDrawableLeft(TextView view, int drawable) {
        android.databinding.adapters.TextViewBindingAdapter.setDrawableLeft(view, ContextCompat.getDrawable(view.getContext(), drawable));

    }

    @BindingAdapter({"android:drawableRight"})
    public static void setDrawableRight(TextView view, int drawable) {
        android.databinding.adapters.TextViewBindingAdapter.setDrawableRight(view, ContextCompat.getDrawable(view.getContext(), drawable));

    }

    @BindingAdapter({"android:drawableTop"})
    public static void setDrawableTop(TextView view, int drawable) {
        android.databinding.adapters.TextViewBindingAdapter.setDrawableTop(view, ContextCompat.getDrawable(view.getContext(), drawable));

    }

    @BindingAdapter({"android:drawableStart"})
    public static void setDrawableStart(TextView view, int drawable) {
        android.databinding.adapters.TextViewBindingAdapter.setDrawableStart(view, ContextCompat.getDrawable(view.getContext(), drawable));
    }

    @BindingAdapter({"android:drawableEnd"})
    public static void setDrawableEnd(TextView view, int drawable) {
        android.databinding.adapters.TextViewBindingAdapter.setDrawableEnd(view, ContextCompat.getDrawable(view.getContext(), drawable));
    }
}
