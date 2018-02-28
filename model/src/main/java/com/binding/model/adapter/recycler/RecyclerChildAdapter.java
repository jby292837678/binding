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

@SuppressWarnings("unchecked")
public class RecyclerChildAdapter<E extends Recycler>
        extends RecyclerBaseAdapter<E, Recycler> implements IModelAdapter<E> {

    private final ArrayList<Recycler> checkList = new ArrayList<>();
    private int max;

    public RecyclerChildAdapter() {
        this(Integer.MIN_VALUE);
    }

    public RecyclerChildAdapter(int max) {
        this.max = max;
    }

    @Override
    protected boolean setISEntity(IModelAdapter<Recycler> eventAdapter, int position, Recycler i, int type, View view) {
        switch (type) {
            case select:
                return select(position, i, view);
            default:
                if (size() == 0) return false;
                E e = getList().get(0);
                return setIEntity(position,e.getClass().isAssignableFrom(i.getClass())?(E)i:null,type,view);
        }
    }

    private boolean isPush(Recycler e) {
        int status = e.checkWay() & 1;
        return status == 1;
    }

    private boolean isTakeBack(Recycler e) {
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
                Recycler e = checkList.get(i);
                if (e == null) continue;
                e.check(false);
            }
    }

    public final boolean select(int position, Recycler e, View v) {
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

    public final boolean select(Recycler in, boolean check, boolean push) {
        boolean success = true;
        Recycler out = null;
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

    private boolean add(Recycler in) {
        boolean success = checkList.size() < max;
        return success && checkList.add(in);
    }


    private Recycler push(Recycler in) {
        checkList.add(in);
        Recycler out = null;
        while (checkList.size() > max)
            out = checkList.remove(0);
        return out;
    }

    public ArrayList<Recycler> getCheckList() {
        return checkList;
    }
}
