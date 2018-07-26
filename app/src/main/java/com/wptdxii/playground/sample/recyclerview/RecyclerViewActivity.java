package com.wptdxii.playground.sample.recyclerview;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.wptdxii.framekit.base.BaseActivity;
import com.wptdxii.framekit.component.recyclerview.multitype.Items;
import com.wptdxii.framekit.component.recyclerview.TypeAdapter;
import com.wptdxii.playground.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);

        toolbar.setTitle(R.string.sample_reyclerview_title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setupMapperRecyclerView();
    }

    private void setupMapperRecyclerView() {
        Items items = new Items();
        for (int i = 0; i < 200; i++) {
            Section item = new Section(String.format("Item: %s", i));
            Banner banner = new Banner(R.drawable.sample_bg_layout_city);
            items.add(new Item(i));
            items.add(item);
            items.add(banner);
        }

        TypeAdapter typeAdapter = new TypeAdapter(4);
        typeAdapter.setItems(items);
        typeAdapter.bind(Banner.class, new BannerBinder())
                .bind(Section.class, new SectionBinder())
                .bind(Item.class)
                .with(new RoundBinder(),
                        new RectBinder())
                .mapper((item, position) -> {
                    if (item.getPosition() % 2 == 0) {
                        return RectBinder.class;
                    } else {
                        return RoundBinder.class;
                    }
                });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(typeAdapter);
    }
}
