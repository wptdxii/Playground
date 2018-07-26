package com.wptdxii.framekit.component.recyclerview;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<T> mItems;

    public BaseAdapter() {
        this(Collections.emptyList());
    }

    public BaseAdapter(@NonNull List<T> items) {
        mItems = items;
    }

    public void setItems(@NonNull List<T> items) {
        mItems = items;
    }

    public List<T> getItems() {
        return mItems;
    }

    public void addItem(T item, @IntRange(from = 0) int position) {
        mItems.add(position, item);
        notifyItemInserted(position);
    }

    public void deleteItem(@IntRange(from = 0) int positon) {
        mItems.remove(positon);
        notifyItemRemoved(positon);
    }

    @NonNull
    protected abstract VH onCreateViewHolder(@NonNull LayoutInflater inflater,
                                             @NonNull ViewGroup parent, int viewType);

    protected abstract void onBindViewHolder(@NonNull VH holder, @NonNull T item);

    @NonNull
    @Override
    public final VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return onCreateViewHolder(inflater, parent, viewType);
    }

    @Override
    public final void onBindViewHolder(@NonNull VH holder, int position) {
        onBindViewHolder(holder, position, Collections.emptyList());
    }

    protected void onBindViewHolder(@NonNull VH holder, @NonNull T item, @NonNull List<Object> payloads) {
        onBindViewHolder(holder, item);
    }

    @Override
    public final void onBindViewHolder(@NonNull VH holder, int position, @NonNull List<Object> payloads) {
        T item = mItems.get(position);
        onBindViewHolder(holder, item, payloads);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
