package com.wptdxii.playground.sample.dagger;

import android.util.Log;

import com.wptdxii.playground.di.scope.ActivityScoped;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Lazy;
import dagger.Provides;
import dagger.Reusable;

public class CoffeeMaker {
    private static final String TAG = "CoffeeMaker";
    private final Lazy<Heater> heater;
    private final Pump pump;

    @Inject
    public CoffeeMaker(Lazy<Heater> heater, Pump pump) {
        this.heater = heater;
        this.pump = pump;
    }

    public void brew() {
        heater.get().on();
        pump.pump();
        Log.e(TAG, "brew: [_]P coffee! [_]P");
        heater.get().off();
    }
}
