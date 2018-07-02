package com.wptdxii.playground.sample.dagger;

import com.wptdxii.playground.di.scope.ActivityScoped;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class DaggerSampleModule {

    /**
     * when create fragment component as subcomponent of AppComponent，We should create
     * Fragment instance manually，then the injection completed by Applicaton，not the Activity
     * attached
     *
     * @return
     */
    @Provides
    @ActivityScoped
    static DaggerSampleFragment provideFragment() {
        return DaggerSampleFragment.newInstance();
    }
}
