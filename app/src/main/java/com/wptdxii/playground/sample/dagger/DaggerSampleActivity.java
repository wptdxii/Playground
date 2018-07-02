package com.wptdxii.playground.sample.dagger;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.wptdxii.playground.R;
import com.wptdxii.playground.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DaggerSampleActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    DaggerSampleFragment mSampleFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity_dagger_sample);
        ButterKnife.bind(this);

        setupToolbar(toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            setupActionBar(actionBar);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        DaggerSampleFragment sampleFragment =
                (DaggerSampleFragment) fragmentManager.findFragmentById(R.id.fl_content);
        if (sampleFragment == null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fl_content, mSampleFragment);
            transaction.commit();
        }
    }

    private void setupActionBar(ActionBar actionBar) {
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setupToolbar(Toolbar toolbar) {
        toolbar.setTitle(R.string.sample_dagger_sample_title);
    }
}
