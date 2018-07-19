package binding.com.demo.ui.home.page;

import android.view.View;

import com.binding.model.adapter.AdapterType;
import com.binding.model.adapter.IEventAdapter;
import com.binding.model.model.ModelView;
import com.binding.model.model.ViewInflateRecycler;

import binding.com.demo.R;

import static com.binding.model.adapter.AdapterType.remove;

@ModelView(R.layout.holder_home_page)
public class HomePageEntity extends ViewInflateRecycler {
    private String text = "aa";

    public String getText() {
        return text+getHolder_position() ;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void onPageClick(View view) {
        switch (getHolder_position()) {
            case 0:
                getIEventAdapter().setEntity(IEventAdapter.NO_POSITION,this,remove,view);
                break;
            case 1:
                this.text = "aaaa";
                getIEventAdapter().setEntity(IEventAdapter.NO_POSITION,this, AdapterType.set,view);
                break;
            case 2:
                getIEventAdapter().setEntity(0,this, AdapterType.move,view);
                break;
        }
    }
}
