package com.wptdxii.playground.sample.dagger.di.module;

import com.wptdxii.playground.sample.dagger.ElectricHeater;
import com.wptdxii.playground.sample.dagger.Heater;
import com.wptdxii.playground.sample.dagger.Pump;
import com.wptdxii.playground.sample.dagger.Thermosiphon;

import javax.inject.Scope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.Reusable;

@Module
public abstract class CoffeeModule {

    @Binds
    abstract Pump privodePump(Thermosiphon thermosiphon);

    @Provides
    static Heater privodeHeater() {
        return new ElectricHeater();
    }
}
