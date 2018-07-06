package com.wptdxii.playground;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.leon.channel.helper.ChannelReaderUtil;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by wptdxii on 2018/1/16
 */

public class App extends Application implements HasActivityInjector {
    private static final String TAG = "App";

    @Inject
    DispatchingAndroidInjector<Activity> mActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();
//        DaggerAppComponent.builder().application(this).build().inject(this);

        String channel = ChannelReaderUtil.getChannel(getApplicationContext());
        Log.e(TAG, "onCreate: " + channel);

    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mActivityInjector;
    }
}
