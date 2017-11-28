package com.binding.library.adapter.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.binding.library.adapter.IEntityAdapter;
import com.binding.library.adapter.ILayoutAdapter;
import com.binding.library.model.inter.Item;
import com.binding.library.util.BaseUtil;

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
    private IEntityAdapter<F> iEntityAdapter;

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
    public boolean setEntity(int position, F e, int type, boolean done) {
        position = position == NO_POSITION ? list.indexOf(e) : position;
        boolean b = iEntityAdapter == null;
        if (b) {
            b = setEntity(position, e, type);
        } else {
            b = iEntityAdapter.setEntity(position, e, type, done);
            if (done) b = setEntity(position, e, type);
        }
        return b;
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
    public void setIEntityAdapter(IEntityAdapter<F> iEntityAdapter) {
        this.iEntityAdapter = iEntityAdapter;
    }
}
