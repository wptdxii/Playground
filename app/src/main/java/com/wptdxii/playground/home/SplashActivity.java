package com.wptdxii.playground.home;

import android.os.Bundle;

import com.wptdxii.framekit.base.BaseActivity;
import com.wptdxii.framekit.util.ScreenUtils;
import com.wptdxii.playground.R;
import com.wptdxii.playground.gank.api.GankApi;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

public class SplashActivity extends BaseActivity {

    private static final int RESIDENCE_TIME = 2;

    @Inject
    GankApi mGankApi;

    private Disposable mSubscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_splash);

        mSubscribe = Flowable.timer(RESIDENCE_TIME, TimeUnit.SECONDS)
                .subscribe(aLong -> {
                    ScreenUtils.cancelFullScreen(SplashActivity.this);
                    HomeActivity.start(this);
                    finish();
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!mSubscribe.isDisposed()) {
            mSubscribe.dispose();
        }
    }
}
