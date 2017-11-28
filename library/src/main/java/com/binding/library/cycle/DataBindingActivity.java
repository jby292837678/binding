package com.binding.library.cycle;

import android.annotation.TargetApi;
import android.arch.lifecycle.LifecycleRegistry;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.binding.library.Config;
import com.binding.library.R;
import com.binding.library.bit.Bit;
import com.binding.library.model.ViewModel;
import com.binding.library.util.BaseUtil;
import com.binding.library.view.swipeback.SwipeBackLayout;

import timber.log.Timber;

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
        String title = getIntent().getStringExtra(Config.title);
        if (!TextUtils.isEmpty(title)) setTitle(title);
        setContentView(getContainer(rootView, isSwipe()));
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
//
//    long start = System.nanoTime();
//
//    public void measureTime() {
//        Timber.i("measure duration time %1d", System.nanoTime() - start);
//        start = System.nanoTime();
//    }

    protected int isSwipe() {
        return 1;
    }

    protected View getContainer(View rootView, int variable) {
        return Bit.bit(variable, (position, aBoolean,container) -> {
            switch (position){
                case 0:
                    if (aBoolean) {
                        container = getLayoutInflater().inflate(R.layout.activity_base, null, false);
                        SwipeBackLayout swipeBackLayout = container.findViewById(R.id.swipeBackLayout);
                        View ivShadow = container.findViewById(R.id.iv_shadow);
                        rootView.setBackgroundResource(R.color.windowBackground);
                        swipeBackLayout.addView(rootView);
                        swipeBackLayout.setOnSwipeBackListener((fa, fs) -> ivShadow.setAlpha(1 - fs));
                        return container;
                    } else {
                        return rootView;
                    }
                default:return container;
            }
        }, 2);
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
//
//    @Override
//    public LifecycleRegistry getLifecycle() {
//        return null;
//    }
}
