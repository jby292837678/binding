package com.binding.model.adapter.list;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.binding.model.R;
import com.binding.model.adapter.AdapterType;
import com.binding.model.adapter.IEventAdapter;
import com.binding.model.adapter.IRecyclerAdapter;
import com.binding.model.model.inter.Inflate;

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


public class ListAdapter<E extends Inflate> extends BaseAdapter implements IRecyclerAdapter<E>, IEventAdapter<E> {
    private final List<E> holderList = new ArrayList<>();
    private IEventAdapter<E> iEventAdapter = this;
    private int count = 0;

    @Override
    public int getCount() {
        return count == 0 || count > holderList.size() ? holderList.size() : count;
    }

    @Override
    public E getItem(int position) {
        return holderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        Inflate inflate = holderList.get(position);
        ViewDataBinding binding;
        if (convertView == null) {
            binding = inflate.attachView(context, parent, false, null);
        } else {
            Inflate out = (Inflate) convertView.getTag(R.id.holder_inflate);
            binding = inflate.attachView(context, parent, false,
                    out.getLayoutId() == inflate.getLayoutId() ? out.getDataBinding() : null);
            out.removeBinding();
        }
        convertView = binding.getRoot();
        convertView.setTag(R.id.holder_inflate, inflate);
        inflate.setIEventAdapter(iEventAdapter);
        return convertView;
    }

    @Override
    public boolean setEntity(int position, E e, int type, View view) {
        boolean outOfList = position<0||position>=holderList.size();
        switch (type) {
            case AdapterType.add:
                if (outOfList) holderList.add(e);
                else holderList.add(position, e);
                break;
            case AdapterType.remove:
                if (holderList.contains(e)) position = holderList.indexOf(e);
                else if (outOfList) return false;
                holderList.remove(position);
                break;
            case AdapterType.set:
                if (!outOfList) holderList.set(position, e);
                break;
            case AdapterType.move:
                if (position < 0) return false;
                if(position>=holderList.size())position = holderList.size()-1;
                int from = holderList.indexOf(e);
                if (from != position && holderList.remove(e)) holderList.add(position, e);
                break;
            case AdapterType.select:
            case AdapterType.refresh:
            case AdapterType.no:
            case AdapterType.onClick:
            case AdapterType.onLongClick:
            default:
                return false;
        }
        notifyDataSetChanged();
        return false;
    }

    @Override
    public boolean setList(int position, List<E> es, int type) {
        boolean outOfList = position<0||position>=holderList.size();
        switch (type) {
            case AdapterType.add:
                if(outOfList)holderList.addAll(es);
                else holderList.addAll(position,es);
                break;
            case AdapterType.refresh:
                List<E> l;
                if (!outOfList) {
                    l = holderList.subList(0, position);
                    l.addAll(es);
                } else l = es;
                holderList.clear();
                holderList.addAll(l);
                break;
            case AdapterType.remove:
            case AdapterType.set:
            case AdapterType.move:
            case AdapterType.select:
            case AdapterType.no:
            case AdapterType.onClick:
            case AdapterType.onLongClick:
            default:return false;
        }
        notifyDataSetChanged();
        return false;
    }



    @Override
    public List<E> getList() {
        return holderList;
    }

    @Override
    public int size() {
        return holderList.size();
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void setIEventAdapter(IEventAdapter<E> iEventAdapter) {
        this.iEventAdapter = iEventAdapter;
    }

}
