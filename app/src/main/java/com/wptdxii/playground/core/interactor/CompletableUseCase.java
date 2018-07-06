package com.wptdxii.playground.core.interactor;

import com.wptdxii.playground.core.schedulers.SchedulerProvider;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;

public abstract class CompletableUseCase<Request extends UseCase.Reqeust> implements UseCase {

    private SchedulerProvider mSchedulerProvider;
    private Disposable mDisposable;

    public CompletableUseCase(SchedulerProvider schedulerProvider) {
        mSchedulerProvider = schedulerProvider;
    }

    protected abstract Completable buildUseCase(Request request);

    public void subscribe(Request request) {
        mDisposable = buildUseCase(request)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe();
    }

    @Override
    public void unsubscribe() {
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
