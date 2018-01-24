package com.wptdxii.framekit.component.executor;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * <p>
 * Executor that runs a task on a new background thread.
 * </p>
 * Created by wptdxii on 2018/1/24
 */

public class DiskIOThreadExecutor implements Executor {

    private final Executor mDiskIO;

    public DiskIOThreadExecutor() {
        this.mDiskIO = Executors.newSingleThreadExecutor();
    }

    @Override
    public void execute(@NonNull Runnable command) {
        this.mDiskIO.execute(command);
    }
}
