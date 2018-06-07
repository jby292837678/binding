package binding.com.demo.ui.home.page;

import com.binding.model.model.ModelView;
import com.binding.model.model.ViewInflateRecycler;

import binding.com.demo.R;

@ModelView(R.layout.holder_home_page)
public class HomePageEntity extends ViewInflateRecycler{
    private String text ="aa";

    public String getText() {
        return text + getHolder_position();
    }

    public void setText(String text) {
        this.text = text;
    }
}
