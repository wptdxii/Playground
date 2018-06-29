package com.wptdxii.framekit.component.executor;

import java.util.concurrent.Executor;

/**
 * Created by wptdxii on 2018/1/24 0024 14:59
 */
public class AppExecutors {
    private final Executor mDiskIO;
    private final Executor mNetworkIO;
    private final Executor mMainThread;

    public AppExecutors() {
        this.mDiskIO = new DiskIOThreadExecutor();
        this.mNetworkIO = new NetworkIOExecutor();
        this.mMainThread = new MainThreadExecutor();
    }

    public Executor getDiskIO() {
        return mDiskIO;
    }

    public Executor getNetworkIO() {
        return mNetworkIO;
    }

    public Executor getMainThread() {
        return mMainThread;
    }
}
