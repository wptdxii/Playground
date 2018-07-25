package com.wptdxii.framekit.component.recyclerview;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class TypePoolImpl implements TypePool {

    private final List<Class<?>> mClasses;
    private final List<ItemViewBinder<?, ?>> mBinders;
    private final List<IndexProvider> mProviders;


    public TypePoolImpl() {
        mClasses = new ArrayList<>();
        mBinders = new ArrayList<>();
        mProviders = new ArrayList<>();
    }

    public TypePoolImpl(int initilCapacity) {
        mClasses = new ArrayList<>(initilCapacity);
        mBinders = new ArrayList<>(initilCapacity);
        mProviders = new ArrayList<>(initilCapacity);
    }

    public TypePoolImpl(@NonNull List<Class<?>> classes,
                        @NonNull List<ItemViewBinder<?, ?>> binders,
                        @NonNull List<IndexProvider> providers) {
        mClasses = classes;
        mBinders = binders;
        mProviders = providers;
    }


    @Override
    public <T> void register(@NonNull Class<? extends T> clazz,
                             @NonNull ItemViewBinder<T, ?> binder,
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
    public Class<?> getClass(int index) {
        return mClasses.get(index);
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
