package com.wptdxii.playground.sample.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wptdxii.framekit.component.recyclerview.BaseAdapter;
import com.wptdxii.playground.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecyclerViewAdapter extends BaseAdapter<Section, RecyclerViewAdapter.ViewHolder> {

    RecyclerViewAdapter(List<Section> items) {
        super(items);
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
                                            @NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.activity_recycler_view_section, parent, false);
        return new ViewHolder(itemView, mItems);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Section item) {
        holder.tvName.setText(item.getName());

        holder.mItem = mItems.get(holder.getAdapterPosition());
    }

    protected final static class ViewHolder extends RecyclerView.ViewHolder {

        private Section mItem;
        @BindView(R.id.tv_name)
        TextView tvName;

        ViewHolder(View itemView, List<Section> items) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.tv_name)
        void showName() {
            Toast.makeText(tvName.getContext(),
                    "TextView:" + mItem.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
