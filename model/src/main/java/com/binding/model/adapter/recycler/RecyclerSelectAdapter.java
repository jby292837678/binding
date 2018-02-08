package com.binding.model.adapter.recycler;

import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;

import com.binding.model.adapter.AdapterHandle;
import com.binding.model.adapter.AdapterType;
import com.binding.model.model.inter.Recycler;
import com.binding.model.util.BaseUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import static com.binding.model.util.BaseUtil.containsList;

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
                for (E e : es) select(e, !checkList.contains(e), isPush(e));
                break;
            default:
                return super.setList(position, es, type);
        }
        return true;
    }

    private boolean isPush(E e) {
        int status = e.checkWay() & 1;
        return status == 1;
    }

    private boolean isTakeBack(E e) {
        int status = e.checkWay() >> 1 & 1;
        return status == 1;
    }

    @Override
    public boolean setIEntity(int position, E e, int type, View view) {
        switch (type) {
            case AdapterType.select:
                return select(position, e, view);
            default:
                return super.setIEntity(position, e, type, view);
        }
    }

    public final void check(int position, boolean check) {
        if (containsList(position, getList())) {
            E e = getList().get(position);
            select(e, check, isPush(e));
        }
    }

    public final void checkAll(boolean check) {
        if (check) {
            for (E e : getList()) {
                if (e == null) continue;
                if (checkList.size() < max) {
                    select(e, true, isPush(e));
                } else break;
            }
        } else
            for (int i = checkList.size() - 1; i >= 0; i--) {
                E e = checkList.get(i);
                if (e == null) continue;
                select(e, false, isPush(e));
            }
    }

    public final boolean select(int position, E e, View v) {
        if (v != null) {
            if (e == null) {
                if (getList().isEmpty()) return false;
                if (!containsList(position, getList())) position = 0;
                e = getList().get(position);
            }
            switch (e.getCheckType()) {
                case ENABLE:
                    return select(e, !v.isEnabled(), isPush(e));
                case CHECK:
                    if (v instanceof Checkable) {
                        return select(e, ((Checkable) v).isChecked(), isPush(e));
                    } else return false;
                case SELECT:
                    return select(e, v.isSelected(), isPush(e));
                default:
                    return select(e, !checkList.contains(e), isPush(e));
            }
        } else return select(e, !checkList.contains(e), isPush(e));
    }

    public final boolean select(E in, boolean check, boolean push) {
        boolean success = true;
        E out = null;
        if (!check) {
            if (!isTakeBack(in)) in.check(true);
            else {
                in.check(false);
                checkList.remove(in);
            }
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
