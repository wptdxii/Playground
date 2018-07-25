package com.wptdxii.framekit.component.recyclerview;

import android.support.annotation.NonNull;

class Mapper<T> implements MapperFlow<T>, MapperEndpoint<T> {

    private final TypeAdapter mTypeAdapter;
    private final Class<? extends T> mClass;
    private ItemViewBinder<T, ?>[] mBinders;

    Mapper(@NonNull TypeAdapter typeAdapter, @NonNull Class<? extends T> clazz) {
        mTypeAdapter = typeAdapter;
        mClass = clazz;
    }

    @Override
    public void binder(BinderClassProvider<T> binderClassProvider) {
        doRegister(ProviderConverter.convert(binderClassProvider, mBinders));
    }

    private void doRegister(IndexProvider<T> convert) {
        for (ItemViewBinder<T, ?> binder : mBinders) {
            mTypeAdapter.register(mClass, binder, convert);
        }
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public MapperEndpoint with(ItemViewBinder<T, ?>... binders) {
        mBinders = binders;
        return this;
    }
}
