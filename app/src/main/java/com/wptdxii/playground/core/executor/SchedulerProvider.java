package com.wptdxii.playground.core.executor;

import io.reactivex.Scheduler;

public interface SchedulerProvider {
    Scheduler getScheduler();
}
