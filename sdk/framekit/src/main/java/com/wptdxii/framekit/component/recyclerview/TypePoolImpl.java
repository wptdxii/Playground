package com.wptdxii.framekit.component.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.wptdxii.framekit.component.recyclerview.multitype.IndexProvider;

import java.util.ArrayList;
import java.util.List;

public class TypePoolImpl implements TypePool {

    private final List<Class<?>> mClasses;
    private final List<ItemViewBinder<?, ?>> mBinders;
    private final List<IndexProvider> mProviders;


    TypePoolImpl() {
        mClasses = new ArrayList<>();
        mBinders = new ArrayList<>();
        mProviders = new ArrayList<>();
    }

    TypePoolImpl(int initialCapacity) {
        mClasses = new ArrayList<>(initialCapacity);
        mBinders = new ArrayList<>(initialCapacity);
        mProviders = new ArrayList<>(initialCapacity);
    }

    @Override
    public <T> void register(@NonNull Class<? extends T> clazz,
                             @NonNull ItemViewBinder<T, ? extends RecyclerView.ViewHolder> binder,
                             @NonNull IndexProvider provider) {
        mClasses.add(clazz);
        mBinders.add(binder);
        mProviders.add(provider);
    }

    @Override
    public void unregister(@NonNull Class<?> clazz) {
        int index = mClasses.indexOf(clazz);
        while (index != -1) {
            mClasses.remove(index);
            mBinders.remove(index);
            mProviders.remove(index);
            index = mClasses.indexOf(clazz);
        }
    }

    @NonNull
    @Override
    public ItemViewBinder<?, ?> getBinder(int index) {
        return mBinders.get(index);
    }

    @NonNull
    @Override
    public IndexProvider getProvider(int index) {
        return mProviders.get(index);
    }

    @Override
    public int firstIndexOf(@NonNull Class<?> clazz) {
        int firstIndex = mClasses.indexOf(clazz);
        if (firstIndex != -1) {
            return firstIndex;
        }

        for (int i = 0; i < mClasses.size(); i++) {
            if (mClasses.get(i).isAssignableFrom(clazz)) {
                return i;
            }
        }
        return -1;
    }
}
