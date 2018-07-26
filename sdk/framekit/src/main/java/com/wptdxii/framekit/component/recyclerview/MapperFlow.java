package com.wptdxii.framekit.component.recyclerview;

import android.support.annotation.NonNull;

import com.wptdxii.framekit.component.recyclerview.multitype.MapperEndpoint;

public interface MapperFlow<T> {

    @NonNull
    @SuppressWarnings("unchecked")
    MapperEndpoint<T> with(@NonNull ItemViewBinder<T, ?>... binders);

}
