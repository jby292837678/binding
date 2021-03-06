package com.binding.model.binding;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.binding.model.R;
import com.binding.model.adapter.IEventAdapter;
import com.binding.model.model.ViewParse;
import com.binding.model.model.inter.Inflate;
import com.binding.model.model.inter.Measure;

import java.util.Collection;
import java.util.List;

/**
 * Created by arvin on 2018/1/17.
 */
@SuppressWarnings("unchecked")
public class ViewGroupBindingAdapter {

    @BindingAdapter("rmInflate")
    public static <E extends Inflate>void removeInflate(ViewGroup viewGroup, E inflate){
        if(inflate == null)return;
        inflate.setIEventAdapter(null);
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            Object object = view.getTag(R.id.addInflate);
            if( inflate.equals(object)){
                viewGroup.removeView(view);
                break;
            }
        }
    }

    @BindingAdapter(value = {"addInflate","eventAdapter"})
    public static <E extends Inflate>void addInflate(ViewGroup viewGroup, E inflate,IEventAdapter<E> eventAdapter){
        if(inflate == null)return;
        inflate.setIEventAdapter(eventAdapter);
        View view = inflate.attachView(viewGroup.getContext(), viewGroup, false, null).getRoot();
        view.setId(inflate.getViewId());
        if(inflate instanceof Measure){
            ViewGroup.LayoutParams params = ((Measure) inflate).measure(view,viewGroup);
            viewGroup.addView(view,params);
        }else {
            viewGroup.addView(view);
        }
        view.setTag(R.id.addInflate,inflate);
    }

    @BindingAdapter("addInflate")
    public static <E extends Inflate>void addInflate(ViewGroup viewGroup, E inflate){
        addInflate(viewGroup, inflate,null);
    }

    @BindingAdapter(value = {"addInflates","eventAdapter"})
    public static <E extends Inflate>void addInflates(ViewGroup group, List<E> inflates, IEventAdapter<E> eventAdapter) {
        group.removeAllViews();
        if (inflates == null || inflates.isEmpty()) return;
        for (E inflate : inflates){
            addInflate(group,inflate,eventAdapter);
        }
    }

    @BindingAdapter("addInflates")
    public static <E extends Inflate>void addInflates(ViewGroup group, List<E> inflates) {
        addInflates(group,inflates,null);
    }

    @BindingAdapter("parses")
    public static void parses(ViewGroup viewGroup, Collection<? extends ViewParse> parses) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        for (ViewParse parse : parses) {
            if (parse instanceof Measure) {
                ViewDataBinding binding = DataBindingUtil.inflate(inflater, parse.getLayoutId(), viewGroup, true);
                binding.getRoot().setLayoutParams(((Measure) parse).measure(binding.getRoot(), viewGroup));
                binding.setVariable(parse.getVariableName(), parse);
            } else {
                ViewDataBinding binding = DataBindingUtil.inflate(inflater, parse.getLayoutId(), viewGroup, true);
                binding.setVariable(parse.getVariableName(), parse);
            }
        }
    }
}
