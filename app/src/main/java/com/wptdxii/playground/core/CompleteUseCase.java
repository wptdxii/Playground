package com.wptdxii.playground.core;

import android.support.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.CompletableTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class CompleteUseCase<Request extends CompleteUseCase.Reqeust> {

    private final Executor mExecutor;
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public CompleteUseCase(@NonNull Executor executor) {
        mExecutor = executor;
    }

    protected abstract Completable buildUseCase(Request request);

    public void subscribe(Request request) {
        Disposable disposable = buildUseCase(request)
                .compose((CompletableTransformer) mExecutor.transformer())
                .subscribe();
        mCompositeDisposable.add(disposable);
    }

    public void unsubscibe() {
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }


    public static interface Reqeust {
    }

}
