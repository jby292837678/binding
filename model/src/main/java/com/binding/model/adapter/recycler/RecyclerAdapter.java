package com.binding.model.adapter.recycler;

import android.view.View;

import com.binding.model.adapter.IModelAdapter;
import com.binding.model.adapter.IRecyclerAdapter;
import com.binding.model.model.inter.Inflate;

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
public class RecyclerAdapter<E extends Inflate>
        extends RecyclerBaseAdapter<E,E> implements IRecyclerAdapter<E>{
    @Override
    boolean setISEntity(IModelAdapter<E> eventAdapter, int position, E e, int type, View view) {
        return eventAdapter.setIEntity(position, e, type, view);
    }
}
//    private AtomicBoolean refresh = new AtomicBoolean(false);
//        if (!refresh.get()) {
//            refresh.set(true);
//            Observable.fromArray(es)
//                    .map(s -> DiffUtil.calculateDiff(new DiffUtilCallback<>(holderList, s)))
//                    .subscribeOn(Schedulers.newThread())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(diffResult -> {
//                                diffResult.dispatchUpdatesTo(this);
//                                holderList.clear();
//                                holderList.addAll(es);
//                                refresh.set(false);
//                            }
//                    );
//        }
