package com.wptdxii.playground.sample.dagger.di.component;

import com.wptdxii.playground.sample.dagger.CoffeeMaker;
import com.wptdxii.playground.sample.dagger.DaggerActivity;
import com.wptdxii.playground.sample.dagger.di.module.CoffeeModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Subcomponent;

@Singleton
@Component(modules = CoffeeModule.class)
public interface CoffeeShopComponent {

    void inject(DaggerActivity daggerActivity);

    CoffeeMaker coffeeMaker();

}
