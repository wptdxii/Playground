package com.wptdxii.playground.core.schedulers;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;

public interface ISchedulerProvider {

    @NonNull
    Scheduler compucation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}
