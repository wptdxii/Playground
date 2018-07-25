package com.wptdxii.framekit.component.recyclerview;

import android.support.annotation.NonNull;

public class ProviderConverter<T> implements IndexProvider<T> {

    private BinderClassProvider<T> mBinderClassProvider;
    private final ItemViewBinder<T, ?>[] mBinders;

    private ProviderConverter(@NonNull BinderClassProvider<T> binderClassProvider,
                              @NonNull ItemViewBinder<T, ?>[] binders) {
        mBinderClassProvider = binderClassProvider;
        mBinders = binders;
    }

    public static <T> IndexProvider<T> convert(@NonNull BinderClassProvider<T> binderClassProvider,
                                               @NonNull ItemViewBinder<T, ?>[] binders) {
        return new ProviderConverter<>(binderClassProvider, binders);
    }

    @Override
    public int index(@NonNull T item, int position) {
        Class<? extends ItemViewBinder<T, ?>> clazz = mBinderClassProvider.binderClass(item, position);
        for (int i = 0; i < mBinders.length; i++) {
            if (mBinders[i].getClass().equals(clazz)) {
                return i;
            }
        }
        throw new RuntimeException("Please link the correct ItemViewBinder!");
    }
}
