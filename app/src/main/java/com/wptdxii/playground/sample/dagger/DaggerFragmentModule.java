package com.wptdxii.playground.sample.dagger;

import android.content.Context;

import com.wptdxii.playground.di.qualifier.ActivityContext;
import com.wptdxii.playground.di.scope.FragmentScoped;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class DaggerFragmentModule {

    @Provides
    @FragmentScoped
    static FragmentDependency provideFragmentDependency(@ActivityContext Context context) {
        return new FragmentDependency(context);
    }
}
