package com.wptdxii.playground.di.component;

import android.app.Application;
import android.content.Context;

import com.wptdxii.playground.di.module.AppModule;
import com.wptdxii.playground.di.qualifier.AppContext;

import dagger.Component;

@Component(modules = {AppModule.class})
public interface AppComponent {

    Application getApplication();

    @AppContext
    Context getContext();
}
