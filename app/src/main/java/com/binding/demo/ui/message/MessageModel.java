package com.binding.demo.ui.message;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;

import com.binding.demo.R;
import com.binding.demo.databinding.ActivityMessageBinding;
import com.binding.demo.inject.qualifier.manager.ActivityFragmentManager;
import com.binding.model.App;
import com.binding.model.adapter.AdapterType;
import com.binding.model.adapter.pager.FragmentStateAdapter;
import com.binding.model.layout.pager.PagerModel;
import com.binding.model.model.ModelView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.binding.model.adapter.IEventAdapter.NO_POSITION;

/**
 * Created by arvin on 2018/1/21.
 */

@ModelView(R.layout.activity_message)
public class MessageModel extends PagerModel<MessageActivity,ActivityMessageBinding,MessageEntity> {
    @Inject MessageModel(@ActivityFragmentManager FragmentManager manager) {super(new FragmentStateAdapter<>(manager));}
    private final List<MessageEntity> list = new ArrayList<>();
    @Override
    public void attachView(Bundle savedInstanceState, MessageActivity messageActivity) {
        super.attachView(savedInstanceState, messageActivity);
        getDataBinding().tabLayout.setTabTextColors(App.getColor(R.color.textColor),App.getColor(R.color.colorPrimary));
        if(list.isEmpty()) for (int i = 0; i < 2; i++) {
            TabLayout.Tab tab1 = getDataBinding().tabLayout.newTab().setText("Tab"+i);
            list.add(new MessageEntity());
            getDataBinding().tabLayout.addTab(tab1);
        }
        getAdapter().setList(NO_POSITION,list, AdapterType.refresh);
    }
}
