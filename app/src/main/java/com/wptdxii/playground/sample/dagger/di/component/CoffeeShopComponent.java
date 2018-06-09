package com.wptdxii.playground.sample.dagger.di.component;

import com.wptdxii.playground.sample.dagger.DaggerActivity;
import com.wptdxii.playground.sample.dagger.di.module.DripCoffeeModule;
import com.wptdxii.playground.sample.dagger.di.module.PumpModule;

import dagger.Component;

@Component(modules = {DripCoffeeModule.class, PumpModule.class})
public interface CoffeeShopComponent {

    void inject(DaggerActivity daggerActivity);
}
