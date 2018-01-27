package com.binding.demo.ui.select;

import android.databinding.ObservableBoolean;
import android.view.View;

import com.binding.demo.R;
import com.binding.model.adapter.AdapterType;
import com.binding.model.adapter.IEventAdapter;
import com.binding.model.model.ModelView;
import com.binding.model.model.ViewInflateRecycler;
import com.binding.model.util.BaseUtil;

import timber.log.Timber;

/**
 * Created by arvin on 2018/1/27.
 */

@ModelView(R.layout.holder_check)
public class SelectEntity extends ViewInflateRecycler{
    public transient ObservableBoolean check = new ObservableBoolean(false);
    private String position;

    public SelectEntity(String position) {
        this.position = position;
    }

    @Override
    public void check(boolean checked) {
        super.check(checked);
        Timber.i("ViewInflateRecycler position:%1d,checked:%1b",getHolder_position(),checked);
        check.set(checked);
    }

    public String getPosition() {
        return "holder:"+getHolder_position()+"position:"+position;
    }

    public String getHolderPosition() {
        return String.valueOf(getHolder_position());
    }

    public void onCheckedClick(View view){
        Timber.i("ViewInflateRecycler click:"+getIEventAdapter()+"getHolder_position():"+getHolder_position());
        if(getIEventAdapter()!=null&&!getIEventAdapter().setEntity(getHolder_position(),this, AdapterType.select,view)){
            BaseUtil.toast("已选满");
        }
    }

    @Override
    public int getCheckType() {
        return IEventAdapter.CHECK;
    }

}
