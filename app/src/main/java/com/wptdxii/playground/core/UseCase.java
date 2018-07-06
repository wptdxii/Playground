package com.wptdxii.playground.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subscribers.DisposableSubscriber;

public abstract class UseCase<Request extends UseCase.Request, Response> {
    private final CompositeDisposable mCompositeDisposable;
    private final Executor<Response> mExecutor;

    public UseCase(@NonNull CompositeDisposable compositeDisposable,
                   @NonNull Executor<Response> executor) {
        mCompositeDisposable = compositeDisposable;
        mExecutor = executor;
    }

    public void subscribe(@Nullable Request request, DisposableSubscriber<Response> useCaseSubscriber) {
        DisposableSubscriber<Response> disposableSubscriber = buildUseCase(request)
                .compose(mExecutor.transformer())
                .subscribeWith(useCaseSubscriber);
        mCompositeDisposable.add(disposableSubscriber);
    }

    public abstract Flowable<Response> buildUseCase(@Nullable Request request);

    public void unsubscribe() {
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
        }
    }

    public interface Request {
    }
}
