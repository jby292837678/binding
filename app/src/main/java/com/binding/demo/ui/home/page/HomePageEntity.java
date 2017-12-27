package com.binding.demo.ui.home.page;

import com.binding.demo.R;
import com.binding.model.model.ModelView;
import com.binding.model.model.ViewInflateRecycler;
import com.binding.model.model.inter.Parse;

/**
 * Created by arvin on 2017/12/8.
 */

@ModelView(R.layout.holder_home_page)
public class HomePageEntity extends ViewInflateRecycler {

    private int id;
    private String name;
    private int age;

    public HomePageEntity(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
