package com.wptdxii.playground.main;

import android.os.Bundle;
import android.view.WindowManager;

import com.wptdxii.playground.R;
import com.wptdxii.playground.base.BaseActivity;

public class SplashActivity extends BaseActivity {
    private static final int RESIDENCE_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().getDecorView().postDelayed(() -> {
            cancelFullScreen();
            MainActivity.start(this);
            finish();
        }, RESIDENCE_TIME);
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
