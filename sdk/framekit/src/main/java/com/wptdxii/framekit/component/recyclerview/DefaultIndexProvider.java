package com.wptdxii.framekit.component.recyclerview;

import android.support.annotation.NonNull;

public class DefaultIndexProvider<T> implements IndexProvider<T> {

    @Override
    public int index(@NonNull T item, int position) {
        return 0;
    }
}
