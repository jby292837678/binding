package com.binding.model.adapter.recycler;

import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;

import com.binding.model.adapter.AdapterHandle;
import com.binding.model.adapter.AdapterType;
import com.binding.model.model.inter.Recycler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：14:47
 * modify developer：  admin
 * modify time：14:47
 * modify remark：
 *
 * @version 2.0
 */

public class RecyclerSelectAdapter<E extends Recycler>
        extends RecyclerAdapter<E> {
    private final ArrayList<E> checkList = new ArrayList<>();
    private int max;

    public RecyclerSelectAdapter() {
        this(Integer.MAX_VALUE);
    }

    public RecyclerSelectAdapter(int max) {
        this.max = max;
    }

    @Override
    public boolean setList(int position, List<E> es, @AdapterHandle int type) {
        if (es == null) return false;
        switch (type) {
            case AdapterType.select:
                for (E e : es) select(e, !checkList.contains(e), e.isPush());
                break;
            default:
                return super.setList(position, es, type);
        }
        return true;
    }


    @Override
    public boolean setEntityView(int position, E e, int type, View view) {
        switch (type) {
            case AdapterType.select:
                return select(e, view);
            default:
                return super.setEntityView(position, e, type, view);
        }
    }

    public final void checkAll(boolean check) {
        checkList.clear();
        for (E e : getList()) {
            if (e == null) continue;
            if (checkList.size() < max) {
                select(e, check , e.isPush());
            }else break;
        }
    }

    public final boolean select(E e, View v) {
        if (v != null)
            switch (e.getCheckType()) {
                case ENABLE:
                    return select(e, v.isEnabled(), e.isPush());
                case CHECK:
                    if (v instanceof Checkable) {
                        return select(e, ((Checkable) v).isChecked(), e.isPush());
                    } else return false;
                case SELECT:
                    return select(e, v.isSelected(), e.isPush());
                default:
                    return select(e, !checkList.contains(e), e.isPush());
            }
        else return select(e, checkList.contains(e), e.isPush());
    }

    public final boolean select(E in, boolean check, boolean push) {
        boolean success = true;
        E out = null;
        if (!check) {
            in.check(false);
            checkList.remove(in);
        } else {
            if (push) {
                out = push(in);
                in.check(true);
            } else {
                success = add(in);
                if (success) in.check(true);
                else out = in;
            }
            if (out != null) out.check(false);
        }
        return success;
    }

    private boolean add(E in) {
        boolean success = checkList.size() < max;
        return success && checkList.add(in);
    }


    private E push(E in) {
        checkList.add(in);
        E out = null;
        while (checkList.size() > max)
            out = checkList.remove(0);
        return out;
    }

    public ArrayList<E> getCheckList() {
        return checkList;
    }
}
