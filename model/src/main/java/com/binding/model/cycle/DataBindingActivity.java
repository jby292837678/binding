package com.binding.model.cycle;

import android.annotation.TargetApi;
import androidx.lifecycle.LifecycleRegistry;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.binding.model.Config;
import com.binding.model.R;
import com.binding.model.util.BaseUtil;
import com.binding.model.view.swipe.SwipeBackLayout;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：12:00
 * modify developer：  admin
 * modify time：12:00
 * modify remark：
 *
 * @version 2.0
 */


public abstract class DataBindingActivity<C> extends AppCompatActivity implements ActivityContainer<C> {
    private Toolbar toolbar;
    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);
    private TextView network_error;
//    private View background;

    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTheme(R.style.AppTheme);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) setTranslucentStatus(true);
        View rootView = inject(savedInstanceState, null, false);
        if (isSwipe() != SwipeBackLayout.FROM_NO) {
            setContentView(R.layout.activity_base);
            SwipeBackLayout swipeBackLayout = findViewById(R.id.swipe_back_layout);
            swipeBackLayout.setDirectionMode(isSwipe());
            swipeBackLayout.addView(rootView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageView imageView = findViewById(R.id.iv_shadow);
            swipeBackLayout.setOnSwipeBackListener((mView, swipeBackFraction) -> imageView.setAlpha(1-swipeBackFraction));
        }else setContentView(rootView);
        initView();
    }

    private void initView() {
        String title = getIntent().getStringExtra(Config.title);
        if (!TextUtils.isEmpty(title)) setTitle(title);
        toolbar = findViewById(R.id.toolbar);
        network_error = findViewById(R.id.network_error);
        if (toolbar == null) return;
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this::onBackClick);
        initToolBar(toolbar);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        if (!TextUtils.isEmpty(getTitle()) && toolbar_title != null)
            toolbar_title.setText(getTitle());
    }

    protected @SwipeBackLayout.DirectionMode int isSwipe() {
        return SwipeBackLayout.FROM_LEFT;
    }

    public abstract void initToolBar(Toolbar view);

    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) winParams.flags |= bits;
        else winParams.flags &= ~bits;
        win.setAttributes(winParams);
    }


    @Override
    public AppCompatActivity getDataActivity() {
        return this;
    }


    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    public void onBackClick(View view) {
        this.onBackPressed();
    }


    /**
     * 5.0  4.4
     *
     * @param toolbar
     * @param styleColor
     */
    public void setToolBarStyle(Toolbar toolbar, View bottomView, int styleColor) {
//        5.0  4.4
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (toolbar != null) {
                int statusHeight = getStatusHeightPx();
                toolbar.setPadding(0, toolbar.getPaddingTop() + statusHeight, 0, 0);
                //下面的导航栏
                if (haveNavigation()) {
                    ViewGroup.LayoutParams layoutParams = bottomView.getLayoutParams();
                    layoutParams.height += getNavigationHeight();
                    bottomView.setLayoutParams(layoutParams);
                    bottomView.setBackgroundColor(styleColor);
                }
            }
        }
    }


    public float getStatusHeightDp() {
        float height = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            String heightStr = clazz.getField("status_bar_height").get(object).toString();
            int id = Integer.parseInt(heightStr);
            height = (int) (getResources().getDimension(id) + 0.5) / BaseUtil.getDisplayMetrics(this).density;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return height;
    }

    public int getStatusHeightPx() {
        int height = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            String heightStr = clazz.getField("status_bar_height").get(object).toString();
            height = Integer.parseInt(heightStr);
            height = getResources().getDimensionPixelSize(height);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return height;
    }

    private int getNavigationHeight() {
        int height = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            String heightStr = clazz.getField("navigation_bar_height").get(object).toString();
            height = Integer.parseInt(heightStr);
            height = getResources().getDimensionPixelSize(height);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return height;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private boolean haveNavigation() {
        //屏幕的高度  真实物理的屏幕
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getRealMetrics(displayMetrics);
        int heightDisplay = displayMetrics.heightPixels;
        //为了防止横屏
        int widthDisplay = displayMetrics.widthPixels;
        DisplayMetrics contentDisplaymetrics = new DisplayMetrics();
        display.getMetrics(contentDisplaymetrics);
        int contentDisplay = contentDisplaymetrics.heightPixels;
        int contentDisplayWidth = contentDisplaymetrics.widthPixels;
        //屏幕内容高度  显示内容的屏幕
        int w = widthDisplay - contentDisplayWidth;
        //哪一方大于0   就有导航栏
        int h = heightDisplay - contentDisplay;
        return w > 0 || h > 0;
    }

}
