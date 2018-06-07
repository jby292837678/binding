package binding.com.demo.ui.address;


import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.binding.model.adapter.IEventAdapter;
import com.binding.model.adapter.recycler.RecyclerSelectAdapter;
import com.binding.model.layout.recycler.RecyclerModel;
import com.binding.model.model.ModelView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import binding.com.demo.R;
import binding.com.demo.base.arouter.ArouterUtil;
import binding.com.demo.databinding.ActivityAddressBinding;
import binding.com.demo.inject.component.ActivityComponent;
import io.reactivex.Observable;

import static com.binding.model.adapter.AdapterType.add;
import static com.binding.model.adapter.AdapterType.select;
import static com.binding.model.adapter.AdapterType.set;
import static com.binding.model.adapter.AdapterType.move;
import static com.binding.model.adapter.IEventAdapter.NO_POSITION;

@ModelView(value = R.layout.activity_address,model = true)
public class AddressModel extends RecyclerModel<AddressActivity, ActivityAddressBinding, AddressEntity> {
    @Inject
    AddressModel() {
        super(new RecyclerSelectAdapter<>(1));
    }
    private int index = 0;

    @Override
    public void attachView(Bundle savedInstanceState, AddressActivity addressActivity) {
        super.attachView(savedInstanceState, addressActivity);
//        getDataBinding().layoutRecycler.setVm(this);
        List<AddressEntity> list = new ArrayList<>();
        for (;index < 20; index++) {
            list.add(new AddressEntity(index));
        }
        setRcHttp((offset1, refresh) -> Observable.fromIterable(list).toList().toObservable());
        addEventAdapter((position, addressEntity, type, view) -> {
            if (type == select&&addressEntity.checked.get()) {
                getAdapter().setIEntity(0, addressEntity, move, view);
                getDataBinding().recyclerView.scrollToPosition(0);
            }
            return false;
        });
        ((RecyclerSelectAdapter<AddressEntity>) getAdapter()).check(0, true);
    }

    public void save(AddressEntity addressEntity){
        int size = getAdapter().size();
        boolean edit= addressEntity.getAddressId() != -1;
        int index = getAdapter().getList().indexOf(addressEntity);
        getAdapter().setEntity(edit?index:size,addressEntity,edit?set:add,null);
    }

    public void onAddClick(View view){
            ArouterUtil.navigation(ActivityComponent.Router.address_edit);
    }

}
