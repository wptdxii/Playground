package com.wptdxii.playground;

import android.app.Application;
import android.util.Log;

import com.leon.channel.helper.ChannelReaderUtil;

/**
 * Created by wptdxii on 2018/1/16
 */

public class App extends Application {
    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        String channel = ChannelReaderUtil.getChannel(getApplicationContext());
        Log.e(TAG, "onCreate: " + channel);
    }
}
