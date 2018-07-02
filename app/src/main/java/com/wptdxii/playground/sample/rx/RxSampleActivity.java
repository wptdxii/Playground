package com.wptdxii.playground.sample.rx;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.wptdxii.playground.R;
import com.wptdxii.playground.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RxSampleActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity_rx_sample);
        ButterKnife.bind(this);

        setupToolbar(toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            setupActionBar(actionBar);
        }
    }

    private void setupActionBar(ActionBar actionBar) {
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setupToolbar(Toolbar toolbar) {
        toolbar.setTitle(R.string.sample_rx_sample_title);
    }
}
