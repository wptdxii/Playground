package com.wptdxii.framekit.component.recyclerview;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wptdxii.framekit.component.recyclerview.multitype.DefaultIndexProvider;
import com.wptdxii.framekit.component.recyclerview.multitype.IndexProvider;
import com.wptdxii.framekit.component.recyclerview.multitype.Items;

import java.util.Collections;
import java.util.List;

public class TypeAdapter extends BaseAdapter<Object, ViewHolder> {

    private final TypePool mTypePool;

    public TypeAdapter() {
        this(new Items());
    }

    @SuppressWarnings("WeakerAccess")
    public TypeAdapter(@NonNull Items items) {
        this(items, new TypePoolImpl());
    }

    public TypeAdapter(@NonNull Items items, @IntRange(from = 0) int initialCapacity) {
        this(items, new TypePoolImpl(initialCapacity));
    }

    public TypeAdapter(@IntRange(from = 0) int initialCapacity) {
        this(new Items(), new TypePoolImpl(initialCapacity));
    }

    private TypeAdapter(@NonNull Items items, @NonNull TypePool typePool) {
        super(items);
        mTypePool = typePool;
    }

    public <T> TypeAdapter bind(@NonNull Class<? extends T> clazz,
                                @NonNull ItemViewBinder<T, ? extends RecyclerView.ViewHolder> binder) {
        mTypePool.unregister(clazz);
        bind(clazz, binder, new DefaultIndexProvider<T>());
        return this;
    }

    public <T> MapperFlow<T> bind(@NonNull Class<? extends T> clazz) {
        mTypePool.unregister(clazz);
        return new Mapper<>(this, clazz);
    }

    <T> void bind(@NonNull Class<? extends T> clazz,
                  @NonNull ItemViewBinder<T, ? extends RecyclerView.ViewHolder> binder,
                  @NonNull IndexProvider indexProvider) {
        mTypePool.register(clazz, binder, indexProvider);
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mItems.get(position);
        return indexInTypesOf(item, position);
    }

    @SuppressWarnings("unchecked")
    private int indexInTypesOf(Object item, int position) {
        int index = mTypePool.firstIndexOf(item.getClass());
        if (index != -1) {
            IndexProvider provider = mTypePool.getProvider(index);
            return index + provider.index(item, position);
        }
        throw new RuntimeException(String.format("Bind the class %S with it's binder first!",
                item.getClass().getSimpleName()));
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
                                            @NonNull ViewGroup parent, int viewType) {
        ItemViewBinder<?, ?> binder = mTypePool.getBinder(viewType);
        return binder.onCreateViewHolder(inflater, parent);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Object item) {
        onBindViewHolder(holder, item, Collections.emptyList());
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Object item, @NonNull List<Object> payloads) {
        ItemViewBinder binder = mTypePool.getBinder(holder.getItemViewType());
        binder.onBindViewHolder(holder, item, payloads);
    }

    @Override
    public Items getItems() {
        return (Items) super.getItems();
    }
}
