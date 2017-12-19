package com.binding.model.adapter.recycler;

import android.view.View;

import com.binding.model.adapter.AdapterHandle;
import com.binding.model.adapter.AdapterType;
import com.binding.model.model.inter.Recycler;

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
    private final HashSet<E> checkList = new HashSet<>();
    private final int max;
    private final ArrayBlockingQueue<E> queue;

    public RecyclerSelectAdapter() {
        this(0);
    }

    public RecyclerSelectAdapter(int max) {
        this.max = max;
        if (max > 0) {
            queue = new ArrayBlockingQueue<>(max);
        } else {
            queue = new ArrayBlockingQueue<>(1);
        }
    }

    @Override
    public boolean setList(int position, List<E> es, @AdapterHandle int type) {
        if (es == null) return false;
        switch (type) {
            case AdapterType.select:for (E e : es) select(e);break;
            default:return super.setList(position, es, type);
        }
        return true;
    }


    @Override
    public boolean setEntity(int position, E e, int type, View view){
        switch (type) {
            case AdapterType.select:select(e);break;
            default:return super.setEntity(position,e,type,view);
        }
        return true;
    }

    public void checkAll(boolean check) {
        for (E e : getList()) e.check(check);
    }

    private void select(E e) {
        if (max > 0) {
            boolean contains = queue.contains(e);
            if (!contains) {
                if (queue.size() == max)
                    queue.poll().check(false);
                e.check(true);
                queue.offer(e);
            } else {
                e.check(false);
            }
        } else {
            boolean c = checkList.contains(e);
            boolean s = c ? checkList.remove(e) : checkList.add(e);
            e.check(s && !c);
        }
    }

    public Collection<E> getChecked() {
        return max > 0 ? queue : checkList;
    }

}
