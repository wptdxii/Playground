package com.wptdxii.playground.sample.dagger;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class DaggerSampleFragmentModule {

    @Binds
    abstract Pump privodePump(Thermosiphon thermosiphon);

    @Provides
    static Heater privodeHeater() {
        return new ElectricHeater();
    }
}
