package com.wptdxii.framekit.component.recyclerview;

import android.support.annotation.NonNull;

public interface MapperFlow<T> {

    @NonNull
    @SuppressWarnings("unchecked")
    MapperEndpoint with(ItemViewBinder<T, ?>... binders);

}
