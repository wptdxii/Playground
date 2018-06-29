package com.wptdxii.playground.sample.di;

import com.wptdxii.playground.di.scope.ActivityScoped;
import com.wptdxii.playground.sample.DrawerActivity;
import com.wptdxii.playground.sample.DrawerFragment;
import com.wptdxii.playground.sample.SampleActivity;
import com.wptdxii.playground.sample.dagger.CoffeeMaker;
import com.wptdxii.playground.sample.dagger.DaggerSampleActivity;
import com.wptdxii.playground.sample.dagger.di.module.CoffeeModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SampleBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = CoffeeModule.class)
    abstract DaggerSampleActivity daggerActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract DrawerActivity drawerActivity();

    @ActivityScoped
    @ContributesAndroidInjector()
    abstract DrawerFragment drawerFragment();

    @ContributesAndroidInjector
    abstract SampleActivity sampleActivity();
}
