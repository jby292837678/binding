package com.binding.model.model;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binding.model.App;
import com.binding.model.adapter.AdapterType;
import com.binding.model.adapter.IEventAdapter;
import com.binding.model.adapter.IModelAdapter;
import com.binding.model.adapter.IRecyclerAdapter;
import com.binding.model.adapter.recycler.RecyclerAdapter;
import com.binding.model.model.inter.Event;
import com.binding.model.model.inter.HttpObservable;
import com.binding.model.model.inter.HttpObservableRefresh;
import com.binding.model.model.inter.Inflate;
import com.binding.model.util.BaseUtil;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by arvin on 2018/1/6.
 */

public class RecyclerInflate<Binding extends ViewDataBinding, E extends Inflate> extends RecyclerView.OnScrollListener
        implements Inflate<Binding>, Event {
    private final IRecyclerAdapter<E> adapter;
    private final boolean page;
    private int lastVisibleItem;
    private int dy;
    public final ObservableBoolean empty = new ObservableBoolean(true);
    public final ObservableBoolean enabled = new ObservableBoolean(true);
    public final ObservableBoolean loading = new ObservableBoolean(false);
    public final ObservableField<String> error = new ObservableField<>("暂无数据");
    public final ObservableField<RecyclerView.LayoutManager> layoutManager = new ObservableField<>();
    private HttpObservable<List<E>> rcHttp;
    private int offset = 0;
    private boolean pagerFlag = true;
    private int pageCount = 16;
    private Disposable disposable;
    private final int spanCount;

    private transient Binding dataBinding;
    private transient IEventAdapter iEventAdapter;
    private final transient ModelView modelView;
    private transient int modelIndex = 0;
    private boolean live = false;

    public RecyclerInflate(IRecyclerAdapter<E> adapter, boolean page,int spanCount) {
        this.adapter = adapter;
        this.page = page;
        this.spanCount = spanCount;
        this.modelView = BaseUtil.findModelView(getClass());
        if(modelView == null)throw new RuntimeException("should to add @ModelView to the class:" + getClass());
    }

    @Override
    public final int getLayoutId(){
        return getLayoutId(getModelIndex());
    }

    public final @LayoutRes int getLayoutId(int viewType){
        int[] layout = getModelView().value();
        int length = layout.length;
        return layout[getModelIndex() < length ? getModelIndex() : 0];
    }

    @Override
    public final ModelView getModelView() {
        return modelView;
    }

    @Override
    public final void setModelIndex(int modelIndex) {
        this.modelIndex =modelIndex;
    }

    @Override
    public int getModelIndex() {
        return modelIndex;
    }

    public final int getVariableName() {
        int[] bindName = getModelView().name();
        int length = bindName.length;
        return getModelIndex() < length ? bindName[getModelIndex()] : App.vm;
    }

    @Override
    public Binding attachView(Context context, ViewGroup co, boolean attachToParent, Binding binding) {
        registerEvent();
        setLayoutManager(spanCount>1?new GridLayoutManager(context,spanCount):new LinearLayoutManager(context));
        return dataBinding =  bind(getLayoutId(),context, co, attachToParent, binding);
    }

    public final <B extends ViewDataBinding>B bind(int layoutId, Context context, ViewGroup co, boolean attachToParent, B binding) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context),layoutId, co, attachToParent);
            binding.setVariable(getVariableName(), this);
        } else {
            binding.setVariable(getVariableName(), this);
            binding.executePendingBindings();
        }
        return binding;
    }

    public final Binding getDataBinding() {
        return dataBinding;
    }

    @Override
    public final void setIEventAdapter(IEventAdapter iEventAdapter) {
        this.iEventAdapter = iEventAdapter;
    }

    @Override
    public final IEventAdapter getIEventAdapter() {
        return iEventAdapter;
    }

    @CallSuper
    @Override
    public void removeBinding() {
        this.dataBinding = null;
        iEventAdapter = null;
        unRegisterEvent();
    }
    @Override
    public final void registerEvent() {
        live = true;
        for (int eventId : getModelView().event()) {
            eventSet.put(eventId, this);
        }
    }

    @Override
    public final void unRegisterEvent() {
        live = false;
        for (int eventId : getModelView().event()) {
            eventSet.remove(eventId);
        }
    }

    public final int event(int eventId, View view, Object... args) {
        return Event.event(eventId, this, view, args);
    }

    public final Disposable getDisposable() {
        return disposable;
    }

    public final void setPagerFlag(boolean pagerFlag) {
        this.pagerFlag = pagerFlag;
    }

    public final IRecyclerAdapter<E> getAdapter() {
        return adapter;
    }

    public final void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager.set(layoutManager);
    }

    public final void setRcHttp(HttpObservableRefresh<List<E>> rcHttp1){
        setRoHttp((offset1, refresh) -> rcHttp1.http(offset1,refresh>0));
    }

    public final void setRoHttp(HttpObservable<List<E>> rcHttp) {
        this.rcHttp = rcHttp;
        onHttp(offset, 0);
    }

    public final void onRefresh() {
        onHttp(0, 1);
    }

    public final void onHttp(int offset, int state) {
        this.offset = offset;
        int p = page ? offset / pageCount + 1 : offset;
        if (rcHttp != null)
            rcHttp.http(p, state).subscribe(this::accept, this::onThrowable, this::onComplete,this::onSubscribe);
    }

    private void onSubscribe(Disposable disposable) {
        this.disposable = disposable;
        loading.set(true);
    }

    private void accept(List<E> list) throws Exception {
        int p = page ? offset / pageCount * pageCount : offset;
        adapter.setList(p, list, AdapterType.refresh);
    }

    private void onThrowable(Throwable throwable) {
        BaseUtil.toast(throwable);
        error.set(throwable.getMessage());
        onComplete();
    }

    @CallSuper
    public void onComplete() {
        loading.set(false);
        empty.set(adapter.size()== 0);
    }

    @Override
    public int onEvent(View view, Event event, Object... args) {
        return 0;
    }

    @Override
    public final void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (!pagerFlag || loading.get() || newState != RecyclerView.SCROLL_STATE_IDLE || dy < 0) return;
        if (lastVisibleItem + 1 >= getAdapter().size()) onHttp(getAdapter().size(), 2);
    }

    @Override
    public final void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        this.dy = dy;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
        }
    }

    public void onErrorClick(View view){
        onHttp(offset,3);
    }

    public void addEventAdapter(IEventAdapter<E> iEventAdapter) {
        getAdapter().addEventAdapter(iEventAdapter);
    }

    @Override
    public boolean isLive() {
        return live;
    }


}
