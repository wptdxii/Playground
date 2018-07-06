package com.wptdxii.playground.core.interactor;

import com.wptdxii.playground.core.schedulers.SchedulerProvider;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;

public abstract class SingleUseCase<Request, Response> {
    private final SchedulerProvider mSchedulerProvider;

    private DisposableSingleObserver<Response> mDisposable;

    public SingleUseCase(SchedulerProvider schedulerProvider) {
        mSchedulerProvider = schedulerProvider;
    }

    protected abstract Single<Response> buildUseCase(Request request);

    public void subscribe(Request request, DisposableSingleObserver<Response> observer) {
        mDisposable = buildUseCase(request)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribeWith(observer);
    }

    @Override
    public void unsubscribe() {
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
