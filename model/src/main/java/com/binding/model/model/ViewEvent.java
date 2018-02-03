package com.binding.model.model;

import android.view.View;

import com.binding.model.model.inter.Event;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：12:59
 * modify developer：  admin
 * modify time：12:59
 * modify remark：
 *
 * @version 2.0
 */


public class ViewEvent extends ViewParse implements Event {
    private transient boolean live = false;

    @Override
    public final void registerEvent() {
        live = true;
        for(int eventId :getModelView().event()){
            eventSet.put(eventId, this);
        }
    }

    @Override
    public final void unRegisterEvent() {
        live = false;
        for(int eventId :getModelView().event()) {
            eventSet.remove(eventId);
        }
    }

    @Override
    public boolean isLive() {
        return live;
    }

    @Override
    public int onEvent(View view, Event event,Object... args) {
        return 0;
    }


    public final int event(int eventId, View view,Object... args) {
        return Event.event(eventId,this,view,args);
    }
}
