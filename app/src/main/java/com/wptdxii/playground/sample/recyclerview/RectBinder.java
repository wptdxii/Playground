package com.wptdxii.playground.sample.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wptdxii.framekit.component.imageloader.ImageLoader;
import com.wptdxii.framekit.component.recyclerview.ItemViewBinder;
import com.wptdxii.playground.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RectBinder extends ItemViewBinder<Item, RectBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.activity_recycler_view_banner, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Item item) {
        ImageLoader.get()
                .load(R.drawable.sample_bg_layout_city)
                .into(holder.ivBanner);
    }

    static final class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_banner)
        ImageView ivBanner;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
