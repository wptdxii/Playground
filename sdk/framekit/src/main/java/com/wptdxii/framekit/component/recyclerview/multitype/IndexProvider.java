package com.wptdxii.framekit.component.recyclerview.multitype;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

public interface IndexProvider<T> {

    @IntRange(from = 0)
    int index(@NonNull T item, int position);
}
