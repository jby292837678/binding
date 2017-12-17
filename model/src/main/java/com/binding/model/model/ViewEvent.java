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

    @Override
    public final void registerEvent() {
        for(int eventId :getModelView().event()){
            eventSet.put(eventId, this);
        }
    }


    public static int event(int eventId, Event event,View view,Object... args){
        Event currentEvent = eventSet.get(eventId);
        return currentEvent==null?0:currentEvent.onEvent(view,event,args);
    }

    public final int event(int eventId, View view,Object... args) {
        return event(eventId,this,view,args);
    }

    @Override
    public final void unRegisterEvent() {
        for(int eventId :getModelView().event()) {
            eventSet.remove(eventId);
        }
    }

    @Override
    public int onEvent(View view, Event event,Object... args) {
        return 0;
    }
}
