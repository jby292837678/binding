package com.binding.model.adapter.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import com.binding.model.adapter.IEventAdapter;
import com.binding.model.adapter.ILayoutAdapter;
import com.binding.model.adapter.IModelAdapter;
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


@SuppressWarnings("unchecked")
public class FragmentStateAdapter<F extends Item<? extends Fragment>> extends FragmentStatePagerAdapter
        implements ILayoutAdapter<F>,IEventAdapter<F> {
    private List<F> list = new ArrayList<>();
    private int count = -1;
    private final IEventAdapter<F> iEventAdapter = this;

    public FragmentStateAdapter(FragmentManager fm) {
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
        return count == -1 ? list.size() : count;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean setList(int position, List<? extends F> e, int type) {
        boolean done = BaseUtil.setList(list, position, e, type);
        if (done) notifyDataSetChanged();
        return done;
    }


    @Override
    public boolean setEntity(int position, F f, int type, View view){
        return setIEntity(position, f, type, view);
    }

    @Override
    public boolean setIEntity(int position, F f, int type, View view) {
        boolean done = BaseUtil.setEntity(list, position, f, type);
        if (done) notifyDataSetChanged();
        return done;
    }

    @Override
    public List<F> getList() {
        return list;
    }


    @Override
    public int getItemPosition(Object object) {
        if (getCount() > 0) return POSITION_NONE;
        return super.getItemPosition(object);
    }

    @Override
    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }
}
