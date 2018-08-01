package com.wptdxii.playground.home;

import android.os.Bundle;
import android.view.WindowManager;

import com.wptdxii.framekit.base.BaseActivity;
import com.wptdxii.playground.R;
import com.wptdxii.playground.gank.api.GankApi;

import javax.inject.Inject;

import timber.log.Timber;

public class SplashActivity extends BaseActivity {

    private static final int RESIDENCE_TIME = 1000;

    @Inject
    GankApi mGankApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_splash);

        getWindow().getDecorView().postDelayed(() -> {
            cancelFullScreen();
            HomeActivity.start(this);
            finish();
        }, RESIDENCE_TIME);

        Timber.d("hello world");
        Timber.d("hell world timeber:e");
        Timber.w("hello wrold timeber:w");
    }

    private void setFullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void cancelFullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

    }
}
