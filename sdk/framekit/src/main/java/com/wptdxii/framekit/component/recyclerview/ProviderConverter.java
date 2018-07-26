package com.wptdxii.framekit.component.recyclerview;

import android.support.annotation.NonNull;

import com.wptdxii.framekit.component.recyclerview.multitype.ClassProvider;
import com.wptdxii.framekit.component.recyclerview.multitype.IndexProvider;

public class ProviderConverter<T> implements IndexProvider<T> {

    private final ClassProvider<T> mClassProvider;
    private final ItemViewBinder<T, ?>[] mBinders;

    private ProviderConverter(@NonNull ClassProvider<T> classProvider,
                              @NonNull ItemViewBinder<T, ?>[] binders) {
        mClassProvider = classProvider;
        mBinders = binders;
    }

    public static <T> IndexProvider convert(@NonNull ClassProvider<T> classProvider,
                                            @NonNull ItemViewBinder<T, ?>[] binders) {
        return new ProviderConverter<>(classProvider, binders);
    }

    @Override
    public int index(@NonNull T item, int position) {
        Class<? extends ItemViewBinder<T, ?>> clazz = mClassProvider.clazz(item, position);
        for (int i = 0; i < mBinders.length; i++) {
            if (mBinders[i].getClass().equals(clazz)) {
                return i;
            }
        }
        throw new RuntimeException(
                String.format("Please bind %s with the %s first!",
                        item.getClass().getSimpleName(), clazz.getSimpleName()));
    }
}
