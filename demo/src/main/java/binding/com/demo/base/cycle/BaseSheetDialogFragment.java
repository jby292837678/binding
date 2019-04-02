package binding.com.demo.base.cycle;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.View;
import android.view.ViewGroup;

import binding.com.demo.inject.component.DaggerFragmentComponent;
import binding.com.demo.inject.component.FragmentComponent;
import binding.com.demo.inject.module.FragmentModule;
import binding.com.demo.ui.DemoApplication;
import com.binding.model.cycle.CycleContainer;
import com.binding.model.model.ViewModel;
import com.binding.model.util.ReflectUtil;

import java.lang.reflect.Method;

import javax.inject.Inject;

/**
 * Created by arvin on 2018/1/23.
 */

public  class BaseSheetDialogFragment<VM extends ViewModel> extends BottomSheetDialogFragment implements CycleContainer<FragmentComponent> {
    private BottomSheetBehavior behavior;
    @Inject
    public VM vm;
    private FragmentComponent component;

    /*
     BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
//        View view = View.inflate(getContext(), R.layout.dialog_bottom_sheet, null);
//        dialog.setContentView(view);
//        behavior = BottomSheetBehavior.from((View) view.getParent());
//        return dialog;
    * */

    public BottomSheetBehavior getBehavior() {
        return behavior;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View rootView = inject(savedInstanceState, null, false);
        dialog.setContentView(rootView);
        behavior = BottomSheetBehavior.from((View) rootView.getParent());
        return dialog;
    }

    @SuppressWarnings("unchecked")
    public final View inject(Bundle savedInstanceState, ViewGroup parent, boolean attachToParent) {
        View view;
        try {
            Method method = FragmentComponent.class.getDeclaredMethod("inject", getClass());
            ReflectUtil.invoke(method, getComponent(), this);
            view = vm.attachContainer(this,parent,attachToParent,savedInstanceState).getRoot();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(String.format("name:%1s need to add @Method inject to FragmentComponent", getClass().getSimpleName()));
        }
        return view;
    }


    @Override
    public final Activity getDataActivity() {
        return getActivity();
    }


    @Override
    public final FragmentComponent getComponent() {
        if (component == null) {
            component = DaggerFragmentComponent.builder()
                    .appComponent(DemoApplication.getAppComponent())
                    .fragmentModule(new FragmentModule(this))
                    .build();
        }
        return component;
    }




    public View initView(View rootView) {
        return rootView;
    }




}
