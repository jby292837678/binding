package binding.com.demo.ui.address;

import android.databinding.ObservableBoolean;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.binding.model.adapter.IEventAdapter;
import com.binding.model.model.ModelView;
import com.binding.model.model.ViewInflateRecycler;

import binding.com.demo.R;
import binding.com.demo.base.arouter.ArouterUtil;
import binding.com.demo.inject.component.ActivityComponent;

import static com.binding.model.adapter.AdapterType.select;


@ModelView(value = {R.layout.holder_address})
public class AddressEntity extends ViewInflateRecycler implements Parcelable {
    private int addressId;
    private String phone = "13282739";
    private String name = "张三";
    private String address = "万里学院";
    public transient ObservableBoolean checked = new ObservableBoolean(false);

    public AddressEntity(int addressId) {
        this.addressId = addressId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public ObservableBoolean getChecked() {
        return checked;
    }

    public void setChecked(ObservableBoolean checked) {
        this.checked = checked;
    }

    @Override
    public void check(boolean checked) {
        super.check(checked);
        this.checked.set(checked);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void onCheckedClick(View view){
//        BaseUtil.toast("check:"+checked.get());
        getIEventAdapter().setEntity(IEventAdapter.NO_POSITION,this,select,view);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.addressId);
        dest.writeString(this.phone);
        dest.writeString(this.name);
        dest.writeString(this.address);
    }

    protected AddressEntity(Parcel in) {
        this.addressId = in.readInt();
        this.phone = in.readString();
        this.name = in.readString();
        this.address = in.readString();
    }

    public static final Parcelable.Creator<AddressEntity> CREATOR = new Parcelable.Creator<AddressEntity>() {
        @Override
        public AddressEntity createFromParcel(Parcel source) {
            return new AddressEntity(source);
        }

        @Override
        public AddressEntity[] newArray(int size) {
            return new AddressEntity[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddressEntity that = (AddressEntity) o;

        return addressId == that.addressId;
    }

    @Override
    public int hashCode() {
        return addressId;
    }

    public void onEditClick(View view){
        Bundle bundle = new Bundle();
        bundle.putParcelable("address",this);
        ArouterUtil.navigation(ActivityComponent.Router.address_edit,bundle);
    }
}
