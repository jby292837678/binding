package com.binding.model.adapter.pager;

import android.databinding.ViewDataBinding;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.binding.model.adapter.IEntityAdapter;
import com.binding.model.adapter.ILayoutAdapter;
import com.binding.model.model.ViewInflate;
import com.binding.model.util.BaseUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：17:33
 * modify developer：  admin
 * modify time：17:33
 * modify remark：
 *
 * @version 2.0
 */


public class ViewPagerAdapter<E extends ViewInflate> extends PagerAdapter implements ILayoutAdapter<E> {
    private List<E> list = new ArrayList<>();
    private int count = Integer.MAX_VALUE;
    private IEntityAdapter<E> iEntityAdapter;

    @Override
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int getCount() {
        return count == Integer.MAX_VALUE ? list.size() : count;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        E e = list.get(position % count);
        ViewDataBinding binding = e.attachView(container.getContext(), container, false, e.getDataBinding());
        View v = binding.getRoot();
        if (container.equals(v.getParent())) container.removeView(v);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position % count).getDataBinding().getRoot());
    }

    @Override
    public boolean setList(int position, List<E> e, int type) {
        boolean done = BaseUtil.setList(list, position, e, type);
        if (done) notifyDataSetChanged();
        return done;
    }

    @Override
    public boolean setEntity(int position, E e, int type, boolean done) {
        position = position == NO_POSITION ? list.indexOf(e) : position;
        boolean b;
        if (iEntityAdapter == null) {
            b = setEntity(position, e, type);
        } else {
            b = iEntityAdapter.setEntity(position, e, type, done);
            if (done) b = setEntity(position, e, type);
        }
        return b;
    }

    @Override
    public boolean setEntity(int position, E f, int type) {
        boolean done = BaseUtil.setEntity(list, position, f, type);
        if (done) notifyDataSetChanged();
        return done;
    }

    @Override
    public int getItemPosition(Object object) {
        if (getCount() > 0) return POSITION_NONE;
        return super.getItemPosition(object);
    }

    @Override
    public List<E> getList() {
        return list;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void setIEntityAdapter(IEntityAdapter<E> iEntityAdapter) {
        this.iEntityAdapter = iEntityAdapter;
    }
}
