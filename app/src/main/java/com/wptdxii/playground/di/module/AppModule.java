package com.wptdxii.playground.di.module;

import android.app.Application;
import android.content.Context;

import com.wptdxii.playground.di.qualifier.AppContext;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final Application mApplication;

    public AppModule(Application application) {
        this.mApplication = application;
    }


    @Provides
    public Application provideApplication() {
        return mApplication;
    }

    @Provides
    @AppContext
    public Context provideContext() {
        return mApplication;
    }
}
