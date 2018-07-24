package com.wptdxii.framekit.component.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public abstract class ItemViewBinder<T, VH extends RecyclerView.ViewHolder> {

    @NonNull
    public abstract VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    public abstract void onBindViewHolder(@NonNull VH holder, T item);
}
