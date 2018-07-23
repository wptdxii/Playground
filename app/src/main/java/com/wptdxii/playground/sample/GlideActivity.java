package com.wptdxii.playground.sample;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.wptdxii.framekit.base.BaseActivity;
import com.wptdxii.framekit.component.imageloader.ImageLoader;
import com.wptdxii.framekit.component.imageloader.LoadCallback;
import com.wptdxii.playground.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GlideActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_glide)
    ImageView ivGlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        ButterKnife.bind(this);

        toolbar.setTitle(R.string.sample_glide_sample_title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ImageLoader.get()
                .load(R.drawable.sample_bg_layout_city)
                .override(100)
                .roundingRadius(30);
    }
}
