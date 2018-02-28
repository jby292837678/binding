package com.binding.model.adapter.recycler;

import android.view.View;
import android.widget.Checkable;

import com.binding.model.adapter.AdapterType;
import com.binding.model.adapter.IEventAdapter;
import com.binding.model.adapter.IModelAdapter;
import com.binding.model.model.inter.Recycler;

import java.util.ArrayList;

import static com.binding.model.adapter.AdapterType.select;

/**
 * Created by arvin on 2018/2/27.
 */

public class RecyclerChildAdapter<E extends Recycler, I extends Recycler>
        extends RecyclerBaseAdapter<E, I> implements IModelAdapter<E> {

    private final ArrayList<I> checkList = new ArrayList<>();
    private int max;

    public RecyclerChildAdapter() {
        this(Integer.MIN_VALUE);
    }

    public RecyclerChildAdapter(int max) {
        this.max = max;
    }

    @Override
    protected boolean setISEntity(IModelAdapter<I> eventAdapter, int position, I i, int type, View view) {
        switch (type) {
            case select:
                return select(position,i,view);
            default:
                return super.setISEntity(eventAdapter, position, i, type, view);
        }
    }

    private boolean isPush(I e) {
        int status = e.checkWay() & 1;
        return status == 1;
    }

    private boolean isTakeBack(I e) {
        int status = e.checkWay() >> 1 & 1;
        return status == 1;
    }


    public final void checkAll(boolean check) {
        if (check) {
            for (E e : getList()) {
                if (e == null) continue;
                if (checkList.size() < max) {
                    e.check(true);
                } else break;
            }
        } else
            for (int i = checkList.size() - 1; i >= 0; i--) {
                I e = checkList.get(i);
                if (e == null) continue;
                e.check(false);
            }
    }

    public final boolean select(int position, I e, View v) {
        if (v != null) {
            if (e == null) {
                return false;
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

    public final boolean select(I in, boolean check, boolean push) {
        boolean success = true;
        I out = null;
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

    private boolean add(I in) {
        boolean success = checkList.size() < max;
        return success && checkList.add(in);
    }


    private I push(I in) {
        checkList.add(in);
        I out = null;
        while (checkList.size() > max)
            out = checkList.remove(0);
        return out;
    }

    public ArrayList<I> getCheckList() {
        return checkList;
    }
}
