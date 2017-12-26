package com.binding.model.layout.recycler;

import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

//import com.binding.library.BR;
import com.binding.model.BR;
import com.binding.model.adapter.IEventAdapter;
import com.binding.model.adapter.IModelAdapter;
import com.binding.model.cycle.Container;
import com.binding.model.layout.ViewArrayModel;
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
public class RecyclerModel<C extends Container,Binding extends ViewDataBinding,E extends Recycler> extends ViewArrayModel<C,Binding,E> {
    public ObservableField<String> empty = new ObservableField<>("");
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = () -> onHttp(1, true);
    private RecyclerView.LayoutManager layoutManager;
    private int lastVisibleItem = 0;
    private boolean pageFlag = true;

    public RecyclerModel(IModelAdapter<E> adapter) {
        super(adapter);
    }

    @Override
    public void attachView(Bundle savedInstanceState, C c) {
        super.attachView(savedInstanceState, c);
        setLayoutManager(new LinearLayoutManager(getT().getDataActivity()));
//        getDataBinding().setVariable(BR.adapter,getAdapter());
    }

    public void onScrollBottom() {}

    @Override
    public void onThrowable(Throwable throwable) {

    }

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        private int dy = 0;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (getAdapter() == null) return;
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 >= getAdapter().size()
                    && !loading.get()) {
                if (pageFlag && dy > 0) onHttp(getAdapter().size()/getPageCount()+1, false);
                onScrollBottom();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            this.dy = dy;
        }
    };

    public RecyclerView.OnScrollListener getScrollListener() {
        return scrollListener;
    }

    public void setPageFlag(boolean pageFlag) {
        this.pageFlag = pageFlag;
    }

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return onRefreshListener;
    }


    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        notifyPropertyChanged(BR.layoutManager);
    }

    @Bindable
    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }


    public void onHttp(View view) {
        onHttp(true);
    }

    public void setEventAdapter(IEventAdapter iEventAdapter) {
        getAdapter().setIEventAdapter(iEventAdapter);
    }
}

