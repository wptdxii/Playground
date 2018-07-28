package com.wptdxii.framekit.base;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.provider.Settings;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.wptdxii.framekit.Extension;
import com.wptdxii.framekit.component.imageloader.ImageLoader;
import com.wptdxii.framekit.component.imageloader.glide.GlideLoaderStrategy;

import org.jetbrains.annotations.NotNull;

import java.security.Security;
import java.util.UUID;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

public abstract class BaseApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> mActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        Extension.install(initExtension());
        initInjector();
        initLogger();
        initImageLoader();
    }

    private void initImageLoader() {
        ImageLoader.get().setLoaderStrategy(new GlideLoaderStrategy());
    }

    private void initLogger() {

        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, @android.support.annotation.Nullable String tag) {
                return Extension.get().isBuildTypeDebug();
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

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mActivityInjector;
    }

    protected abstract Extension initExtension();

    protected abstract void initInjector();
}
