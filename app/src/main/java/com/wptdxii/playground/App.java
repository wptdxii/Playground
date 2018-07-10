package com.wptdxii.playground;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.leon.channel.helper.ChannelReaderUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.wptdxii.playground.di.component.DaggerAppComponent;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

/**
 * Created by wptdxii on 2018/1/16
 */

public class App extends Application implements HasActivityInjector {
    private static final String TAG = "App";
    private static final String TAG_LOGGER = "Logger";

    @Inject
    DispatchingAndroidInjector<Activity> mActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        initInjector();
        initLogger();

        String channel = ChannelReaderUtil.getChannel(getApplicationContext());
        Log.e(TAG, "onCreate: " + channel);

    }

    private void initLogger() {

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag(TAG_LOGGER)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, @android.support.annotation.Nullable String tag) {
                return BuildConfig.DEBUG;
            }
        });

        //        Logger.addLogAdapter(new DiskLogAdapter());

        Timber.plant(new Timber.DebugTree() {
            @Override
            protected void log(int priority, String tag, @NotNull String message, Throwable t) {
                Logger.log(priority, tag, message, t);
            }
        });
    }

    private void initInjector() {
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mActivityInjector;
    }
}
