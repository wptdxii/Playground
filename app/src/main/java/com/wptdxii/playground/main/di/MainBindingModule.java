package com.wptdxii.playground.main.di;

import com.wptdxii.playground.main.MainActivity;
import com.wptdxii.playground.main.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainBindingModule {

    @ContributesAndroidInjector
    abstract SplashActivity splashActivity();

    @ContributesAndroidInjector
    abstract MainActivity mainActivity();
}
