package com.wptdxii.framekit.component.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private List<T> mItems;

    public BaseAdapter(List<T> items) {
        mItems = items;
    }

    public void setItems(List<T> items) {
        mItems = items;
    }

    public List<T> getItems() {
        return mItems;
    }

    @NonNull
    @Override
    public abstract VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        onBindViewHolder(holder, position, Collections.emptyList());
    }

    protected abstract void onBindViewHolder(@NonNull VH holder, @NonNull T item, @NonNull List<Object> payloads);

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position, @NonNull List<Object> payloads) {
        T item = mItems.get(position);
        onBindViewHolder(holder, item, payloads);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
