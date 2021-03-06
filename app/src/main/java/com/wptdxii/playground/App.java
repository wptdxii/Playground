package com.wptdxii.playground;

import com.leon.channel.helper.ChannelReaderUtil;
import com.wptdxii.framekit.Extension;
import com.wptdxii.framekit.base.BaseApplication;
import com.wptdxii.playground.di.component.DaggerAppComponent;

import timber.log.Timber;

public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        String channel = ChannelReaderUtil.getChannel(getApplicationContext());
        Timber.d("channel:%s", channel);
    }

    @Override
    protected Extension initExtension() {
        return new Extension.Builder()
                .application(this)
                .buildType(BuildConfig.DEBUG)
                .build();
    }

    @Override
    protected void initInjector() {
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);
    }
}
