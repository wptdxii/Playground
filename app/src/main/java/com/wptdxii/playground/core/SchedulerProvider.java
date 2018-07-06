package com.wptdxii.playground.core;

import io.reactivex.Scheduler;

public interface SchedulerProvider {

    Scheduler provideScheduler();
}
