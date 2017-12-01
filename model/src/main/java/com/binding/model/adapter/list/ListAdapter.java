package com.binding.model.adapter.list;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.databinding.library.baseAdapters.BR;
import com.binding.model.R;
import com.binding.model.adapter.IEventAdapter;
import com.binding.model.adapter.IRecyclerAdapter;
import com.binding.model.model.inter.Parse;

import java.util.ArrayList;
import java.util.List;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：10:58
 * modify developer：  admin
 * modify time：10:58
 * modify remark：
 *
 * @version 2.0
 */


public class ListAdapter<E extends Parse> extends BaseAdapter implements IRecyclerAdapter<E> {
    private List<E> list = new ArrayList<>();
    private int count = 0;
    private Class<?> c;

    @Override
    public int getCount() {
        return count == 0 || count > list.size() ? list.size() : count;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        ViewDataBinding binding;
        Parse parse = list.get(position);
        int layoutId = parse.getModelView().value()[parse.getModelIndex()];
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(R.id.holder_list, binding);
            convertView.setTag(R.id.holder_layoutId, layoutId);
        } else {
            if (layoutId == (int) convertView.getTag(R.id.holder_layoutId)) {
                binding = (ViewDataBinding) convertView.getTag(R.id.holder_list);
            } else {
                binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, parent, false);
                convertView = binding.getRoot();
                convertView.setTag(R.id.holder_list, binding);
                convertView.setTag(R.id.holder_layoutId, layoutId);
            }
        }
        binding.setVariable(BR.vm, list.get(position));
        binding.executePendingBindings();
        return convertView;
    }

    //    -----------------------------------  IModelAdapter ------------------------

    @Override
    public boolean setList(int position, List<E> e, int type) {
        return false;
    }


    @Override
    public boolean setEntity(int position, E e, int type, View view){
        return false;
    }

    @Override
    public List<E> getList() {
        return list;
    }

    @Override
    public int size() {
        return list.size();
    }

    public void setCount(int count) {
        this.count = count;
    }

    private IEventAdapter<E> iEventAdapter;


    @Override
    public void setIEventAdapter(IEventAdapter<E> iEntityAdapter) {
        this.iEventAdapter = iEventAdapter;
    }
}
