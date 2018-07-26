package com.wptdxii.framekit.component.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.wptdxii.framekit.component.recyclerview.multitype.IndexProvider;

public interface TypePool {

    <T> void register(@NonNull Class<? extends T> clazz,
                      @NonNull ItemViewBinder<T, ? extends RecyclerView.ViewHolder> binder,
                      @NonNull IndexProvider provider);

    void unregister(@NonNull Class<?> clazz);

    @NonNull
    ItemViewBinder<?, ?> getBinder(int index);

    @NonNull
    IndexProvider getProvider(int index);

    int firstIndexOf(@NonNull Class<?> clazz);
}
