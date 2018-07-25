package com.wptdxii.framekit.component.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

public interface TypePool {

    <T> void register(@NonNull Class<? extends T> clazz,
                      @NonNull ItemViewBinder<T, ?> binder,
                      @NonNull IndexProvider provider);

    void unregister(@NonNull Class<?> clazz);

    @NonNull
    Class<?> getClass(int index);

    @NonNull
    <T, VH extends RecyclerView.ViewHolder> ItemViewBinder<T, VH> getBinder(int index);

    @NonNull
    IndexProvider getProvider(int index);

    int firstIndexOf(@NonNull Class<?> clazz);
}
