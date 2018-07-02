package com.wptdxii.playground.sample.di;

import com.wptdxii.playground.di.scope.ActivityScoped;
import com.wptdxii.playground.di.scope.FragmentScoped;
import com.wptdxii.playground.sample.DrawerActivity;
import com.wptdxii.playground.sample.DrawerFragment;
import com.wptdxii.playground.sample.SampleActivity;
import com.wptdxii.playground.sample.dagger.DaggerSampleActivity;
import com.wptdxii.playground.sample.dagger.DaggerSampleFragment;
import com.wptdxii.playground.sample.dagger.DaggerSampleFragmentModule;
import com.wptdxii.playground.sample.dagger.DaggerSampleModule;
import com.wptdxii.playground.sample.rx.RxSampleActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SampleBindingModule {

    /**
     * Subcomponent of AppComponent not Activity
     *
     * @return
     */
    @FragmentScoped
    @ContributesAndroidInjector(modules = DaggerSampleFragmentModule.class)
    abstract DaggerSampleFragment daggerSampleFragment();

    @ActivityScoped
    @ContributesAndroidInjector(modules = DaggerSampleModule.class)
    abstract DaggerSampleActivity daggerSampleActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract DrawerActivity drawerActivity();

    @ActivityScoped
    @ContributesAndroidInjector()
    abstract DrawerFragment drawerFragment();

    @ContributesAndroidInjector
    abstract SampleActivity sampleActivity();

    @ContributesAndroidInjector
    abstract RxSampleActivity rxSampleActivity();
}
