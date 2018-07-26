package com.wptdxii.framekit.component.recyclerview;

import android.support.annotation.NonNull;

import com.wptdxii.framekit.component.recyclerview.multitype.ClassProvider;
import com.wptdxii.framekit.component.recyclerview.multitype.IndexProvider;
import com.wptdxii.framekit.component.recyclerview.multitype.MapperEndpoint;

class Mapper<T> implements MapperFlow<T>, MapperEndpoint<T> {

    private final TypeAdapter mTypeAdapter;
    private final Class<? extends T> mClass;
    private ItemViewBinder<T, ?>[] mBinders;

    public Mapper(@NonNull TypeAdapter typeAdapter, @NonNull Class<? extends T> clazz) {
        mTypeAdapter = typeAdapter;
        mClass = clazz;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public MapperEndpoint<T> with(@NonNull ItemViewBinder<T, ?>... binders) {
        mBinders = binders;
        return this;
    }

    @Override
    public void mapper(ClassProvider<T> classProvider) {
        doRegister(ProviderConverter.convert(classProvider, mBinders));
    }

    private void doRegister(IndexProvider convert) {
        for (ItemViewBinder<T, ?> binder : mBinders) {
            mTypeAdapter.bind(mClass, binder, convert);
        }
    }
}
