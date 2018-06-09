package com.wptdxii.playground.sample.dagger;

import android.util.Log;

import javax.inject.Inject;

public class Thermosiphon implements Pump {
    private static final String TAG = "Thermosiphon";
    private final Heater heater;

    @Inject
    public Thermosiphon(Heater heater) {
        this.heater = heater;
    }

    @Override
    public void pump() {
        Log.e(TAG, "pump: => => pumping => =>");
    }
}
