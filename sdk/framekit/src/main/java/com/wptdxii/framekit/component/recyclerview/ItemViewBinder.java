package com.wptdxii.framekit.component.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

public abstract class ItemViewBinder<T, VH extends RecyclerView.ViewHolder> {

    @NonNull
    protected abstract VH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent);

    protected abstract void onBindViewHolder(@NonNull VH holder, @NonNull T item);

    protected void onBindViewHolder(@NonNull VH holder, @NonNull T item, @NonNull List<Object> payloads) {
        onBindViewHolder(holder, item);
    }
}
