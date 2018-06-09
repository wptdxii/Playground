package com.wptdxii.playground;

import android.app.Application;
import android.util.Log;

import com.leon.channel.helper.ChannelReaderUtil;
import com.wptdxii.playground.di.component.AppComponent;
import com.wptdxii.playground.di.component.DaggerAppComponent;
import com.wptdxii.playground.di.module.AppModule;

/**
 * Created by wptdxii on 2018/1/16
 */

public class App extends Application {
    private static final String TAG = "App";
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        String channel = ChannelReaderUtil.getChannel(getApplicationContext());
        Log.e(TAG, "onCreate: " + channel);

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
