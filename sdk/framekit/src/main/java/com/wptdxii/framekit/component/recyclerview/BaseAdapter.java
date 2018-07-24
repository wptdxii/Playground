package com.wptdxii.framekit.component.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private List<T> mList;

    public BaseAdapter(@NonNull List<T> list) {
        mList = list;
    }

    public void setList(@NonNull List<T> list) {
        mList = list;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        T item = mList.get(position);
        onBindViewHolder(holder, item);
    }

    protected abstract void onBindViewHolder(@NonNull VH holder, T item);

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
