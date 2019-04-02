package com.binding.model.layout.recycler;

import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.binding.model.adapter.IEventAdapter;
import com.binding.model.adapter.recycler.RecyclerBaseAdapter;
import com.binding.model.cycle.Container;
import com.binding.model.model.ViewArrayModel;
import com.binding.model.model.inter.Inflate;
import com.binding.model.model.inter.Recycler;
import com.binding.model.model.request.RecyclerStatus;

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
public class RecyclerBaseModel<C extends Container, Binding extends ViewDataBinding, E extends Inflate,S extends Recycler>
        extends ViewArrayModel<C, Binding, E,RecyclerBaseAdapter<E,S>> {
    public ObservableField<RecyclerView.LayoutManager> layoutManager = new ObservableField<>();
    private boolean pageFlag = true;

    public RecyclerBaseModel(RecyclerBaseAdapter<E,S> adapter) {
        super(adapter);
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
        onHttp(0, RecyclerStatus.click);
    }

    public void addEventAdapter(IEventAdapter<S> iEventAdapter) {
        getAdapter().addEventAdapter(iEventAdapter);
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
                    onHttp(getAdapter().size(),RecyclerStatus.loadBottom);
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

