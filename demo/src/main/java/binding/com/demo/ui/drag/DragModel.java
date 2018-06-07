package binding.com.demo.ui.drag;

import android.os.Bundle;

import com.binding.model.model.ModelView;
import com.binding.model.model.ViewModel;

import javax.inject.Inject;
import binding.com.demo.databinding.ActivityDragBinding;
import binding.com.demo.R;

@ModelView(R.layout.activity_drag)
public class DragModel extends ViewModel <DragActivity,ActivityDragBinding>{
    @Inject DragModel() {
    }


    @Override
    public void attachView(Bundle savedInstanceState, DragActivity dragActivity) {
        super.attachView(savedInstanceState, dragActivity);

    }
}
