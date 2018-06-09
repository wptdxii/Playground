package com.wptdxii.playground.sample.dagger.di.module;

import com.wptdxii.playground.sample.dagger.ElectricHeater;
import com.wptdxii.playground.sample.dagger.Heater;

import dagger.Module;
import dagger.Provides;

@Module
public class DripCoffeeModule {
    @Provides
    public static Heater provideHeater() {
        return new ElectricHeater();
    }
}
