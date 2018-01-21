package com.binding.demo.ui.message;

import android.os.Bundle;
import android.view.ViewGroup;

import com.binding.demo.base.cycle.BaseFragment;
import com.binding.demo.ui.Constant;
import com.binding.demo.ui.message.fragment.MessageFragment;
import com.binding.model.model.ViewParse;
import com.binding.model.model.inter.Item;

/**
 * Created by arvin on 2018/1/21.
 */

public class MessageEntity extends ViewParse implements Item<MessageFragment>{
    private transient MessageFragment fragment;
    @Override
    public MessageFragment getItem(int position, ViewGroup container) {
        if(fragment == null)fragment = new MessageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.position,position);
        fragment.setArguments(bundle);
        return fragment;
    }
}
