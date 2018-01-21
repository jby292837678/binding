package com.binding.demo.ui.message.fragment;

import android.os.Bundle;

import com.binding.demo.R;
import com.binding.demo.databinding.FragmentMessageBinding;
import com.binding.demo.ui.Constant;
import com.binding.model.App;
import com.binding.model.model.ModelView;
import com.binding.model.model.ViewModel;

import javax.inject.Inject;

/**
 * Created by arvin on 2018/1/21.
 */

@ModelView(R.layout.fragment_message)
public class MessageFragmentModel extends ViewModel<MessageFragment, FragmentMessageBinding> {
    @Inject
    MessageFragmentModel() {
    }

    @Override
    public void attachView(Bundle savedInstanceState, MessageFragment messageFragment) {
        super.attachView(savedInstanceState, messageFragment);
        Bundle bundle = messageFragment.getArguments();
        int position = bundle.getInt(Constant.position);
        if (position == 0)
            getDataBinding().lin.setBackgroundColor(App.getColor(R.color.colorAccent));
        else
            getDataBinding().lin.setBackgroundColor(App.getColor(R.color.colorPrimary));
    }
}
