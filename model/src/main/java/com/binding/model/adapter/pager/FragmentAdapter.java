package com.binding.model.adapter.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.binding.model.adapter.IEventAdapter;
import com.binding.model.adapter.ILayoutAdapter;
import com.binding.model.model.inter.Item;
import com.binding.model.util.BaseUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：22:15
 * modify developer：  admin
 * modify time：22:15
 * modify remark：
 *
 * @version 2.0
 */


public class FragmentAdapter<F extends Item<? extends Fragment>> extends FragmentPagerAdapter implements ILayoutAdapter<F> {
    private List<F> list = new ArrayList<>();
    private int count = -1;
    private IEventAdapter<F> iEventAdapter;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position).getItem(position, null);
    }

    @Override
    public int getCount() {
        return count <= 0 ? list.size() : count;
    }

    @Override
    public int size() {
        return list.size();
    }


    @Override
    public int getItemPosition(Object object) {
        if (getCount() > 0)
            return POSITION_NONE;
        return super.getItemPosition(object);
    }

    @Override
    public boolean setList(int position, List<F> e, int type) {
        boolean done = BaseUtil.setList(list, position, e, type);
        if (done) notifyDataSetChanged();
        return done;
    }

    @Override
    public boolean setEntity(int position, F f, int type) {
        boolean done = BaseUtil.setEntity(list, position, f, type);
        if (done) notifyDataSetChanged();
        return done;
    }

    @Override
    public List<F> getList() {
        return list;
    }

    @Override
    public void setIEventAdapter(IEventAdapter<F> iEventAdapter) {
        this.iEventAdapter = iEventAdapter;
    }
}
