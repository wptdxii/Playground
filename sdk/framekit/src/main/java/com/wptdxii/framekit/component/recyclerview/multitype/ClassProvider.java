package com.wptdxii.framekit.component.recyclerview.multitype;

import android.support.annotation.NonNull;

import com.wptdxii.framekit.component.recyclerview.ItemViewBinder;

public interface ClassProvider<T> {

    @NonNull
    Class<? extends ItemViewBinder<T, ?>> clazz(@NonNull T item, int position);
}
