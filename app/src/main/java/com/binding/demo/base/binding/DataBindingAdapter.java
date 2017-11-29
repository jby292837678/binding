package com.binding.demo.base.binding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.adapters.TextViewBindingAdapter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.binding.model.model.ViewParse;
import com.binding.model.model.inter.Inflate;
import com.binding.model.model.inter.Measure;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.Collection;
import java.util.List;


/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：9:51
 * modify developer：  admin
 * modify time：9:51
 * modify remark：
 *
 * @version 2.0
 */


public class DataBindingAdapter {

    //    --------------------------View------------------------
    @BindingAdapter("android:slide_list")
    public static void setVisibility(View view, int visibility) {
        if (visibility != view.getVisibility()) {
            view.setVisibility(visibility);
        }
    }

    @BindingAdapter("onLongClick")
    public static void setOnLongClick(View view, View.OnLongClickListener listener) {
        view.setOnLongClickListener(listener);
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter("inflates")
    public static void addInflates(ViewGroup group, List<? extends Inflate> inflates) {
        group.removeAllViews();
        if (inflates == null || inflates.isEmpty()) return;
        for (Inflate inflate : inflates)
            inflate.attachView(group.getContext(), group, true, null).getRoot();
    }




    @BindingAdapter("params")
    public static void setLayoutParams(View view, ViewGroup.LayoutParams params) {
        view.setLayoutParams(params);
    }

    @BindingAdapter({"android:background"})
    public static void setBackground(View view, String imageUrl) {
//        Context mContext = view.getContext();
//        Glide.with(mContext).load(imageUrl).into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                Drawable drawable = new BitmapDrawable(mContext.getResources(), resource);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                    view.setBackground(drawable);
//                } else {
//                    view.setBackgroundDrawable(drawable);
//                }
//            }
//        });
     Context mContext = view.getContext();
        Glide.with(mContext)
                .load(imageUrl)

                .into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.setBackground(resource);
                } else {
                    view.setBackgroundDrawable(resource);
                }
            }
        });
    }

    @BindingAdapter("background_blur")
    public static void backgroundBlur(View view, String url) {
//        Context mContext = view.getContext();
//        Glide.with(mContext)
//                .load(url)
//                .bitmapTransform(new BlurTransformation(mContext))
//                .into(new SimpleTarget<GlideDrawable>() {
//                    @Override
//                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                            view.setBackground(resource);
//                        } else {
//                            view.setBackgroundDrawable(resource);
//                        }
//                    }
//                });
    }

    @BindingAdapter("parses")
    public static void parses(ViewGroup viewGroup, Collection<? extends ViewParse> parses) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        for (ViewParse parse : parses) {
            if (parse instanceof Measure) {
                ViewDataBinding binding = DataBindingUtil.inflate(inflater, parse.getLayoutId(), viewGroup, true);
                binding.getRoot().setLayoutParams(((Measure) parse).measure(binding.getRoot(), viewGroup));
                binding.setVariable(parse.getVariableName(), parse);
            } else {
                ViewDataBinding binding = DataBindingUtil.inflate(inflater, parse.getLayoutId(), viewGroup, true);
                binding.setVariable(parse.getVariableName(), parse);
            }
        }
    }

    //    --------------------------ProgressBar------------------------------
    @BindingAdapter("android:secondaryProgress")
    public static void setSecondaryProgress(ProgressBar bar, int progress) {
        bar.setSecondaryProgress(progress);
    }

    //    --------------------------RadioButton------------------------------
    @BindingAdapter("sound")
    public static void setSoundEffectsEnabled(RadioButton button, boolean sound) {
        button.setSoundEffectsEnabled(sound);
    }


    //    --------------------------ImageView------------------------
    @BindingAdapter({"android:src"})
    public static void setImageDrawable(ImageView imageView, String imageUrl) {
        Context context = imageView.getContext();
        Glide.with(context)
                .load(imageUrl)
//                .placeholder(R.mipmap.img_default2_normal)
//                .error(R.mipmap.img_default2_normal)
                .into(imageView);
    }

    @BindingAdapter("circle")
    public static void setImageCircleDrawable(ImageView imageView, String url) {
        Context context = imageView.getContext();
//        Glide.with(context).load(url)
//                .bitmapTransform(new CropCircleTransformation(context))
//                .error(R.mipmap.home_borrow_checked)
//                .placeholder(R.mipmap.home_borrow_checked)
//                .into(imageView);
    }

    @BindingAdapter("head")
    public static void head(ImageView imageView, String url) {
//        Context context = imageView.getContext();
//        Glide.with(context)
//                .load(url)
//                .bitmapTransform(new CropCircleTransformation(context))
////                .error(R.mipmap.def_icon_head_normal)
////                .placeholder(R.mipmap.def_icon_head_normal)
//                .into(imageView);
    }

    @BindingAdapter({"android:src", "radius"})
    public static void setImageRadiusDrawable(ImageView imageView, String url, int radius) {
//        Context context = imageView.getContext();
//        radius = (int) (radius * BaseUtil.getDisplayMetrics(context).density + 0.5f);
//        Glide.with(context)
//                .load(url)
//                .bitmapTransform(new RoundedCornersTransformation(context, radius, 0, RoundedCornersTransformation.CornerType.ALL))
////                .error(R.mipmap.home_borrow_checked)
////                .placeholder(R.mipmap.home_borrow_checked)
//                .into(imageView);
    }


    @BindingAdapter("android:src")
    public static void setImageDrawable(ImageView view, @DrawableRes int mipmapId) {
        if (mipmapId != 0) view.setImageResource(mipmapId);
    }

    //    --------------------------SeekBar------------------------
    @BindingAdapter("listener")
    public static void setOnSeekBarChangeListener(SeekBar bar, SeekBar.OnSeekBarChangeListener listener) {
        bar.setOnSeekBarChangeListener(listener);
    }

    //    --------------------------TextView------------------------
    @BindingAdapter({"android:drawableTop"})
    public static void setDrawableTop(TextView view, String image) {
        Context mContext = view.getContext();
        Glide.with(mContext).load(image).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable drawable, Transition<? super Drawable> transition) {
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                Drawable[] drawables = view.getCompoundDrawables();
                view.setCompoundDrawables(drawables[0], drawable, drawables[2], drawables[3]);
            }
        });
    }

    @BindingAdapter({"android:drawableBottom"})
    public static void setDrawableBottom(TextView view, int drawable) {
        TextViewBindingAdapter.setDrawableBottom(view, ContextCompat.getDrawable(view.getContext(), drawable));
    }

    @BindingAdapter({"android:drawableLeft"})
    public static void setDrawableLeft(TextView view, int drawable) {
        TextViewBindingAdapter.setDrawableLeft(view, ContextCompat.getDrawable(view.getContext(), drawable));

    }

    @BindingAdapter({"android:drawableRight"})
    public static void setDrawableRight(TextView view, int drawable) {
        TextViewBindingAdapter.setDrawableRight(view, ContextCompat.getDrawable(view.getContext(), drawable));

    }

    @BindingAdapter({"android:drawableTop"})
    public static void setDrawableTop(TextView view, int drawable) {
        TextViewBindingAdapter.setDrawableTop(view, ContextCompat.getDrawable(view.getContext(), drawable));

    }

    @BindingAdapter({"android:drawableStart"})
    public static void setDrawableStart(TextView view, int drawable) {
        TextViewBindingAdapter.setDrawableStart(view, ContextCompat.getDrawable(view.getContext(), drawable));
    }

    @BindingAdapter({"android:drawableEnd"})
    public static void setDrawableEnd(TextView view, int drawable) {
        TextViewBindingAdapter.setDrawableEnd(view, ContextCompat.getDrawable(view.getContext(), drawable));
    }


    @BindingAdapter("loadUrl")
    public static void loadWebUrl(WebView view,String url){
        if(TextUtils.isEmpty(url))view.loadUrl(url);
    }

}
