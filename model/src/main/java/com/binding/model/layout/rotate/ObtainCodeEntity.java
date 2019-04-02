package com.binding.model.layout.rotate;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Created by pc on 2017/8/29.
 */

public class ObtainCodeEntity implements TimeEntity,LifecycleObserver {
    private int time;
    private PagerRotateListener<Integer> pagerRotateListener;

    public ObtainCodeEntity(){

    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public void getTurn() {
        if (--time == 0)
            TimeUtil.getInstance().remove(this);
        if (pagerRotateListener != null) pagerRotateListener.nextRotate(time);
//        measureTime();
    }

//    private long start =  System.nanoTime();
//    public void measureTime(){
//        Timber.i("measure duration time %1f ms",(System.nanoTime() -start)/1000d);
//        start = System.nanoTime();
//    }

    public void setPagerRotateListener(PagerRotateListener<Integer> pagerRotateListener) {
        this.pagerRotateListener = pagerRotateListener;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop(){
        TimeUtil.getInstance().remove(this);
    }


}
