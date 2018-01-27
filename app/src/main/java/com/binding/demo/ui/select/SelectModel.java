package com.binding.demo.ui.select;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.binding.demo.R;
import com.binding.demo.databinding.ActivitySelectBinding;
import com.binding.model.adapter.AdapterType;
import com.binding.model.adapter.recycler.RecyclerSelectAdapter;
import com.binding.model.layout.recycler.RecyclerModel;
import com.binding.model.model.ModelView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.binding.model.adapter.IEventAdapter.NO_POSITION;

/**
 * Created by arvin on 2018/1/27.
 */

@ModelView(R.layout.activity_select)
public class SelectModel extends RecyclerModel<SelectActivity,ActivitySelectBinding,SelectEntity>{
    @Inject SelectModel(){super(new RecyclerSelectAdapter<>(2));}

    @Override
    public void attachView(Bundle savedInstanceState, SelectActivity selectActivity) {
        super.attachView(savedInstanceState, selectActivity);
        List<SelectEntity> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new SelectEntity(String.valueOf(i)));
        }
        getAdapter().setList(NO_POSITION,list, AdapterType.add);
    }

    public void onCheckClick(View view){
        RecyclerSelectAdapter<SelectEntity> adapter = (RecyclerSelectAdapter<SelectEntity>)getAdapter() ;
        adapter.checkAll(((CheckBox)view).isChecked());
    }
}
