package binding.com.demo.ui.address.edit;



import android.os.Bundle;
import android.view.View;

import com.binding.model.model.ModelView;
import com.binding.model.model.ViewModel;
import com.binding.model.model.inter.Model;

import javax.inject.Inject;

import binding.com.demo.R;
import binding.com.demo.databinding.ActivityAddressEditBinding;
import binding.com.demo.ui.address.AddressEntity;

@ModelView(R.layout.activity_address_edit)
public class AddressEditModel extends ViewModel<AddressEditActivity,ActivityAddressEditBinding> {
    @Inject AddressEditModel() {}
    private AddressEntity addressEntity;

    @Override
    public void attachView(Bundle savedInstanceState, AddressEditActivity addressEditActivity) {
        super.attachView(savedInstanceState, addressEditActivity);
        addressEntity = addressEditActivity.getIntent().getParcelableExtra("address");
        if(addressEntity == null)addressEntity = new AddressEntity(-1);
        getDataBinding().setEntity(addressEntity);
    }

    public void onSaveClick(View view){
        Model.dispatchModel("save",addressEntity);
        finish();
    }
}
