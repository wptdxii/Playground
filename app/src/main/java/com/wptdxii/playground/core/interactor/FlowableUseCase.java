package com.wptdxii.playground.core.interactor;

import com.wptdxii.playground.core.schedulers.SchedulerProvider;

import io.reactivex.Flowable;
import io.reactivex.subscribers.DisposableSubscriber;

public abstract class FlowableUseCase<Request extends UseCase.Reqeust, Response> implements UseCase {

    private final SchedulerProvider mSchedulerProvider;
    private DisposableSubscriber<Response> mDisposable;

    public FlowableUseCase(SchedulerProvider schedulerProvider) {
        mSchedulerProvider = schedulerProvider;
    }


    protected abstract Flowable<Response> buildUseCase(Request request);

    public void subscribe(Request request, DisposableSubscriber<Response> subscriber) {

        mDisposable = buildUseCase(request)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribeWith(subscriber);
    }

    @Override
    public void unsubscribe() {
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
