package com.wptdxii.playground.di.module;

import android.app.Application;
import android.content.Context;

import com.wptdxii.playground.di.qualifier.AppContext;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class AppModule {

    @AppContext
    @Binds
    abstract Context provideContext(Application application);
}
