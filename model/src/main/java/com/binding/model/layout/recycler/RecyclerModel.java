package com.binding.model.layout.recycler;

import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.binding.model.adapter.IEventAdapter;
import com.binding.model.adapter.IModelAdapter;
import com.binding.model.adapter.recycler.RecyclerAdapter;
import com.binding.model.cycle.Container;
import com.binding.model.layout.ViewArrayModel;
import com.binding.model.model.inter.Inflate;
import com.binding.model.model.inter.Recycler;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：9:19
 * modify developer：  admin
 * modify time：9:19
 * modify remark：
 *
 * @version 2.0
 */
public class RecyclerModel<C extends Container, Binding extends ViewDataBinding, E extends Inflate> extends ViewArrayModel<C, Binding, E> {
    public ObservableField<RecyclerView.LayoutManager> layoutManager = new ObservableField<>();
    private boolean pageFlag = true;

    public RecyclerModel(IModelAdapter<E> adapter) {
        super(adapter);
    }

    public RecyclerModel() {
        this(new RecyclerAdapter<>());
    }

    @Override
    public void attachView(Bundle savedInstanceState, C c) {
        super.attachView(savedInstanceState, c);
        setLayoutManager(new LinearLayoutManager(getT().getDataActivity()));
    }

    public RecyclerView.OnScrollListener getScrollListener() {
        return scrollListener;
    }

    public void setPageFlag(boolean pageFlag) {
        this.pageFlag = pageFlag;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager.set(layoutManager);
    }

    public void onHttp(View view) {
        onHttp(3);
    }

    public void setEventAdapter(IEventAdapter iEventAdapter) {
        getAdapter().setIEventAdapter(iEventAdapter);
    }

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        private int lastVisibleItem = 0;
        private int dy = 0;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (getAdapter() == null) return;
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 >= getAdapter().size()
                    && !loading.get()) {
                if (pageFlag && dy > 0) {
                    onHttp(getAdapter().size(), 2);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            this.dy = dy;
        }
    };
}

