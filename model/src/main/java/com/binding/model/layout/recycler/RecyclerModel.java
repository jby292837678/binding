package com.binding.model.layout.recycler;

import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.binding.model.adapter.IEventAdapter;
import com.binding.model.adapter.recycler.DiffUtilCallback;
import com.binding.model.adapter.recycler.RecyclerAdapter;
import com.binding.model.cycle.Container;
import com.binding.model.cycle.MainLooper;
import com.binding.model.model.ViewArrayModel;
import com.binding.model.model.inter.HttpObservable;
import com.binding.model.model.inter.Inflate;
import com.binding.model.model.request.RecyclerStatus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

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
        extends ViewArrayModel<C, Binding, E, RecyclerAdapter<E>> implements ListUpdateCallback {
    public ObservableField<RecyclerView.LayoutManager> layoutManager = new ObservableField<>();
    private boolean pageFlag = true;
    protected final List<E> holderList = new ArrayList<>();

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
        onHttp(headIndex, RecyclerStatus.click);
    }

    public final void addEventAdapter(IEventAdapter<E> iEventAdapter) {
        getAdapter().addEventAdapter(iEventAdapter);
    }

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        private boolean isLast;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (getAdapter() == null) return;
            if (newState == RecyclerView.SCROLL_STATE_IDLE && isLast) {
                onHttp(getAdapter().size(), RecyclerStatus.loadBottom);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
//            int lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition()findLastVisibleItemPosition();
            isLast = dy>0&& pageFlag&&!loading.get()&&lastVisibleItem+1>=getAdapter().size();
        }
    };

    @Override
    protected void http(HttpObservable<? extends List<? extends E>> rcHttp, int p, int refresh) {
        rcHttp.http(p, refresh)
                .flatMap(entities ->
                        MainLooper.isUiThread()
                                ? Observable.just(entities).doOnNext(super::onNext)
                                : Observable.just(entities)
                                .map(es -> refresh(offset, es))
                                .doOnNext(this::compare)
                                .map(es -> DiffUtil.calculateDiff(new DiffUtilCallback<>(getAdapter().getList(), es)))
                                .observeOn(AndroidSchedulers.mainThread())
                                .map(this::setToAdapter))
                .subscribe(this);
    }


    public void compare(List<? extends E> es) {

    }

    public void onNext(List<? extends E> es) {

    }


    public List<E> refresh(int p, List<? extends E> es) {
        holderList.clear();
        holderList.addAll(es);
        List<E> list = getAdapter().getList();
        if (p == list.size() || list.isEmpty())
            holderList.addAll(0, list);
        if (containsList(p, list))
            holderList.addAll(0, list.subList(0, p));
        return holderList;
    }

    protected List<E> setToAdapter(DiffUtil.DiffResult diffResult) {
        diffResult.dispatchUpdatesTo(this);
        getAdapter().getList().clear();
        getAdapter().getList().addAll(holderList);
        return holderList;
    }

    @Override
    public final RecyclerAdapter<E> getAdapter() {
        return super.getAdapter();
    }

    @Override
    public void onInserted(int position, int count) {
        getAdapter().notifyItemRangeInserted(position, count);
    }

    @Override
    public void onRemoved(int position, int count) {
        getAdapter().notifyItemRangeRemoved(position, count);
    }

    @Override
    public void onMoved(int fromPosition, int toPosition) {
        getAdapter().notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onChanged(int position, int count, Object payload) {
        getAdapter().notifyItemRangeChanged(position, count, payload);
    }
}

