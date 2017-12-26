package com.binding.model.adapter.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.binding.model.adapter.IEventAdapter;
import com.binding.model.model.inter.Inflate;
import com.binding.model.model.inter.SpanSize;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arvin on 2017/12/21.
 */

public class RecyclerBaseAdapter<E extends Inflate>
        extends RecyclerView.Adapter<RecyclerHolder<E>>{
    protected final List<E> holderList = new ArrayList<>();
    protected IEventAdapter iEventAdapter;
    private final SparseArray<E> sparseArray = new SparseArray<>();
    private int count;



    @Override
    public RecyclerHolder<E> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerHolder<>(parent,sparseArray.get(viewType));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    E e = holderList.get(position);
                    int spanSize = 1;
                    if (e instanceof SpanSize) spanSize = ((SpanSize) e).getSpanSize();
                    return spanSize;
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
    }

    @Override
    public void onBindViewHolder(RecyclerHolder<E> holder, int position) {
        E e = holderList.get(position);
        holder.executePendingBindings(e, iEventAdapter);
    }

    @Override
    public int getItemViewType(int position) {
        E e = holderList.get(position);
        int viewType = e.getLayoutId();
        sparseArray.put(viewType, e);
        return viewType;
    }

    @Override
    public int getItemCount() {
        return count == 0 || count > holderList.size() ? holderList.size() : count;
    }

    public void setCount(int count) {
        this.count = count;
    }



    public final boolean moveListAdapter(int position, List<E> es) {
        return moveListAdapter(position, es, holderList);
    }

    protected boolean moveListAdapter(int position, List<E> es, List<E> holderList) {
        for (int i = 0; i < es.size(); i++)
            moveToAdapter(position + i, es.get(i), holderList);
        return isRang(position,es,holderList)>=0;
    }

    protected int isRang(int position, List<? extends E> es, List<? extends E> holderList) {
        if(es.isEmpty())return -2;
        int rang = holderList.indexOf(es.get(0));
        for (int i = 0; i < es.size(); i++) {
            if (holderList.indexOf(es.get(i)) == position + i)continue;
            rang = -1;
            break;
        }
        return rang;
    }

    public final boolean moveToAdapter(int position, E e) {
        return moveToAdapter(position, e, holderList);
    }

    protected boolean moveToAdapter(int position, E e, List<E> holderList) {
        if ( position < 0) return false;
        if(position>=holderList.size())position = holderList.size()-1;
        int from = holderList.indexOf(e);
        if (from != position && holderList.remove(e)) {
            holderList.add(position, e);
            notifyItemMoved(from, position);
            return true;
        }
        return false;
    }
}
