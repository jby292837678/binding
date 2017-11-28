package com.binding.library.layout.pager;

import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;

import com.binding.library.adapter.IModelAdapter;
import com.binding.library.cycle.Container;
import com.binding.library.layout.ViewArrayModel;
import com.binding.library.layout.rotate.PagerEntity;
import com.binding.library.layout.rotate.PagerRotateListener;
import com.binding.library.layout.rotate.TimeUtil;
import com.binding.library.model.inter.Parse;

import java.util.List;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：9:30
 * modify developer：  admin
 * modify time：9:30
 * modify remark：
 *
 * @version 2.0
 */

//@ModelView(value = {R.layout.layout_radio_pager})
public class PagerModel<C extends Container, Binding extends ViewDataBinding, E extends Parse>
        extends ViewArrayModel<C,  Binding,E>
        implements PagerRotateListener<E>, ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {
    private int loop = -1;
    private PagerEntity<E> pagerEntity;
    public ObservableInt currentItem = new ObservableInt(0);
    public ObservableInt position = new ObservableInt(0);
    private boolean rotate = false;
    private int count = 0;

    public PagerModel(IModelAdapter<E> adapter) {
        super(adapter);
    }

    @Override
    public void attachView(Bundle savedInstanceState, C c) {
        super.attachView(savedInstanceState, c);
        TimeUtil.getInstance().remove(pagerEntity);
    }

    public void setLoop(int loop) {
        this.loop = loop;
    }

    @Override
    public void nextRotate(E e) {
        if (rotate && (loop == -1 || --loop > 0)) setCurrentItem(getData().indexOf(e));
    }

    public void setCurrentItem(int currentItem) {
        this.currentItem.set(currentItem);
    }

    @Override
    public void accept(List<E> es) throws Exception {
        super.accept(es);
        pagerEntity = new PagerEntity<>(es, this);
        if (rotate) TimeUtil.getInstance().add(pagerEntity);
        pagerEntity.addRotateListener(this);
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        int indexOfChild = group.indexOfChild(group.findViewById(checkedId));
        setCurrentItem(indexOfChild);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.position.set(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (rotate) TimeUtil.getInstance().switching(pagerEntity, state);
    }

    public void setRotate(boolean rotate) {
        this.rotate = rotate;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        TimeUtil.getInstance().remove(pagerEntity);
    }
}
