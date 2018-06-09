package com.wptdxii.playground.sample.dagger;

public class ElectricHeater implements Heater {
    private boolean heating = false;

    @Override
    public void on() {
        this.heating = true;
    }

    @Override
    public void off() {
        this.heating = false;
    }

    @Override
    public boolean isHot() {
        return heating;
    }
}
