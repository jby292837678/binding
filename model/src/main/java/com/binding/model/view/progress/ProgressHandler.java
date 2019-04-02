package com.binding.model.view.progress;


import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by pc on 2017/8/31.
 */

public class ProgressHandler implements Handler.Callback{
    private Handler handler = new Handler(this);
    private static final int SUCCESS = 0;
    private static final int FAILED = 1;
    private static final int START = 2;
    private static final int PROGRESS = 3;
    private static final int CANCEL = 4;
    private ProgressDialog dialog;

    public void setDialog(ProgressDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what){
            case SUCCESS:
            case FAILED:
            case START:
            case PROGRESS:
            case CANCEL:
        }
        return false;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SUCCESS,FAILED,START,PROGRESS,CANCEL})
    public @interface ProgressState{

    }

    public interface Callback{

    }
}
