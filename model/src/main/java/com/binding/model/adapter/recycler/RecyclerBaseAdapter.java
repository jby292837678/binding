package com.binding.model.adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.binding.model.adapter.IEventAdapter;
import com.binding.model.adapter.IModelAdapter;
import com.binding.model.adapter.IRecyclerAdapter;
import com.binding.model.model.inter.Inflate;
import com.binding.model.util.ReflectUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arvin on 2017/12/21.
 */

@SuppressWarnings("unchecked")
public class RecyclerBaseAdapter<E extends Inflate,I extends Inflate>
        extends RecyclerView.Adapter<RecyclerHolder<E>> implements IEventAdapter<I>{
    protected final List<E> holderList = new ArrayList<>();
    private final SparseArray<E> sparseArray = new SparseArray<>();
    private int count;
    protected final IEventAdapter<I> iEventAdapter = this;
    private final List<IEventAdapter<I>> eventAdapters = new ArrayList<>();

    @Override
    public RecyclerHolder<E> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerHolder<>(parent,sparseArray.get(viewType));
    }

    @Override
    public void onBindViewHolder(RecyclerHolder<E> holder, int position) {
        E e = holderList.get(position);
        holder.executePendingBindings(position,e, iEventAdapter);
    }

    @Override
    public int getItemViewType(int position) {
        E e = holderList.get(position);
        int viewType = e.getLayoutId();
        sparseArray.put(viewType, e);
        return viewType;
    }

    @Override
    public boolean setEntity(int position, I i, int type, View view) {
        for (IEventAdapter<I> eventAdapter : eventAdapters) {
            if(eventAdapter instanceof IModelAdapter){
                try{
                    return ((IModelAdapter) eventAdapter).setIEntity(position,i,type,view);
                }catch (ClassCastException e){
                    e.printStackTrace();
                }
            }else if(eventAdapter.setEntity(position, i, type, view))return true;
        }
        return false;
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

    public void addEventAdapter(IEventAdapter<I> eventAdapter) {
        eventAdapters.add(0, eventAdapter);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        eventAdapters.add(iEventAdapter);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        eventAdapters.clear();
    }
}
