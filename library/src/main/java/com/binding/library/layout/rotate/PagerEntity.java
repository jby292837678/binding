package com.binding.library.layout.rotate;

import com.binding.library.layout.ViewArrayModel;
import com.binding.library.model.inter.Parse;

import java.util.ArrayList;
import java.util.List;


/**
 * @param <E> model is the view page Model
 */
public class PagerEntity<E extends Parse> implements TimeEntity{
    private int totalTime;
    private int time = 0;
    private int index = 0;
    private int lastIndex = 0;
    private int loop = -1;
    private int count = 0;
    private List<E> list = new ArrayList<>();
    private ViewArrayModel<?,?,E> model;
    private List<PagerRotateListener<E>> pagerRotateListeners = new ArrayList<>();

    public PagerEntity(List<E> list, ViewArrayModel<?,?,E> model) {
        this(3, list, model);
    }

    public PagerEntity(int totalTime, List<E> list, ViewArrayModel<?,?,E> model) {
        this.model = model;
        this.totalTime = totalTime;
        if (list != null) {
            this.list.clear();
            this.list.addAll(list);
            this.index = list.size() - 1;
            count = list.size();
        }
    }



    public ViewArrayModel getModel() {
        return model;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setLoop(int loop) {
        this.loop = loop;
    }

    public int getCheckIndex(E model) {
        return list.indexOf(model);
    }

    public PagerEntity<E> addRotateListener(PagerRotateListener<E> listener) {
        pagerRotateListeners.add(listener);
        return this;
    }

    @Override
    public void getTurn() {
        if (totalTime == getTime()) {
            E model = getType();
            if (model != null)
                if (loop == -1 || --loop > 0) {
                    for (PagerRotateListener<E> pagerRotateListener : pagerRotateListeners)
                        pagerRotateListener.nextRotate(model);
                }
        }
    }

    private int getCheckIndex() {
        lastIndex = index;
        return index = index == count - 1 ? 0 : ++index;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    public void setIndex(int index) {
        lastIndex = this.index;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public E getType() {
        if (count != 0) return list.get(getCheckIndex() % count);
        return null;
    }


    public int getTotalTime() {
        return totalTime;
    }

    public int getTime() {
        return time = time == 0 ? totalTime : --time;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list.clear();
        this.list.addAll(list);
        if (count == 0) count = list.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PagerEntity<?> that = (PagerEntity<?>) o;
        return model.equals(that.model);

    }

    @Override
    public int hashCode() {
        return model.hashCode();
    }
}