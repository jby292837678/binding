package com.binding.model.layout.recycler;

import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.binding.model.adapter.IEventAdapter;
import com.binding.model.adapter.IRecyclerAdapter;
import com.binding.model.adapter.recycler.DiffUtilCallback;
import com.binding.model.adapter.recycler.RecyclerAdapter;
import com.binding.model.adapter.recycler.RecyclerBaseAdapter;
import com.binding.model.cycle.Container;
import com.binding.model.cycle.MainLooper;
import com.binding.model.model.ViewArrayModel;
import com.binding.model.model.ViewHttpModel;
import com.binding.model.model.ViewInflate;
import com.binding.model.model.inter.HttpObservable;
import com.binding.model.model.inter.Inflate;
import com.binding.model.model.inter.Recycler;

import java.util.ArrayList;
import java.util.List;

import static com.binding.model.util.BaseUtil.containsList;

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

public class RecyclerModel<C extends Container, Binding extends ViewDataBinding, E extends Inflate>
        extends ViewArrayModel<C, Binding, E, RecyclerAdapter<E>> {
    public ObservableField<RecyclerView.LayoutManager> layoutManager = new ObservableField<>();
    private boolean pageFlag = true;
    private final List<E> holderList =new ArrayList<>();

    public RecyclerModel(RecyclerAdapter<E> adapter) {
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

    public final RecyclerView.OnScrollListener getScrollListener() {
        return scrollListener;
    }

    public final void setPageFlag(boolean pageFlag) {
        this.pageFlag = pageFlag;
    }

    public final void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager.set(layoutManager);
    }

    public final void onHttp(View view) {
        onHttp(0, 3);
    }

    public final void addEventAdapter(IEventAdapter<E> iEventAdapter) {
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
                    onHttp(getAdapter().size(), 0);
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

    @Override
    protected void http(HttpObservable<List<E>> rcHttp, int p, int refresh) {
        if (MainLooper.isUiThread()) super.http(rcHttp, p, refresh);
        else addDisposable(rcHttp.http(p, refresh)
                .map(es -> refresh(p, es))
                .map(es -> DiffUtil.calculateDiff(new DiffUtilCallback<>(getAdapter().getList(), es)))
                .flatMap(ViewHttpModel::fromToMain)
                .subscribe(this::setToAdapter,
                        this::onThrowable,
                        this::onComplete,
                        this::onSubscribe));
    }


    public List<E> refresh(int p, List<E> es) {
        List<E> list = getAdapter().getList();
        if (p == list.size() || list.isEmpty())
            es.addAll(0, list);
        if (containsList(p, list))
            es.addAll(0, list.subList(0,p));
        holderList.clear();
        holderList.addAll(es);
        return es;
    }

    private void setToAdapter(DiffUtil.DiffResult diffResult) {
        diffResult.dispatchUpdatesTo(getAdapter());
        getAdapter().getList().clear();
        getAdapter().getList().addAll(holderList);
    }

    @Override
    public final RecyclerAdapter<E> getAdapter() {
        return super.getAdapter();
    }
}

