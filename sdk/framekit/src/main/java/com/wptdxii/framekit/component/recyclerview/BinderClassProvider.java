package com.wptdxii.framekit.component.recyclerview;

import android.support.annotation.NonNull;

public interface BinderClassProvider<T> {

    @NonNull
    Class<? extends ItemViewBinder<T, ?>> binderClass(@NonNull T item, int positon);
}
