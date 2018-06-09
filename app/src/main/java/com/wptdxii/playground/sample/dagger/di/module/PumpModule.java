package com.wptdxii.playground.sample.dagger.di.module;

import com.wptdxii.playground.sample.dagger.Pump;
import com.wptdxii.playground.sample.dagger.Thermosiphon;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class PumpModule {

    @Binds
    abstract Pump providePump(Thermosiphon pump);
}
