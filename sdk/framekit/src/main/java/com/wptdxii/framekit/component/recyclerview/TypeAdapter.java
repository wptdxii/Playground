package com.wptdxii.framekit.component.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

public class TypeAdapter extends BaseAdapter<Object, ViewHolder> {

    private TypePool mTypePool;

    public TypeAdapter(@NonNull List<Object> items) {
        this(items, new TypePoolImpl());
    }

    public TypeAdapter(@NonNull List<Object> items, int initialCapacity) {
        this(items, new TypePoolImpl(initialCapacity));
    }

    public TypeAdapter(@NonNull List<Object> items, @NonNull TypePool typePool) {
        super(items);
        mTypePool = typePool;
    }


    public <T> void register(@NonNull Class<? extends T> clazz,
                             @NonNull ItemViewBinder<T, ?> binder) {
        mTypePool.unregister(clazz);
        register(clazz, binder, new DefaultIndexProvider<>());
    }

    public <T> MapperEndpoint<T> register(@NonNull Class<? extends T> clazz) {
        mTypePool.unregister(clazz);
        return new Mapper<>(this, clazz);
    }

    <T> void register(@NonNull Class<? extends T> clazz,
                      @NonNull ItemViewBinder<T, ?> binder,
                      @NonNull IndexProvider<T> indexProvider) {
        mTypePool.register(clazz, binder, indexProvider);
    }

    @Override
    public int getItemViewType(int position) {
        Object item = getItems().get(position);
        return indexInTypesOf(item, position);
    }

    private int indexInTypesOf(Object item, int position) {
        int index = mTypePool.firstIndexOf(item.getClass());
        if (index != -1) {
            IndexProvider provider = mTypePool.getProvider(index);
            return index + provider.index(item, position);
        }
        throw new BinderNotFoundException(item.getClass());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemViewBinder<?, ?> binder = mTypePool.getBinder(viewType);
        return binder.onCreateViewHolder(inflater, parent);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Object item, @NonNull List<Object> payloads) {
        ItemViewBinder<Object, ViewHolder> binder = mTypePool.getBinder(holder.getItemViewType());
        binder.onBindViewHolder(holder, item, payloads);
    }
}
