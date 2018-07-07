package com.wptdxii.playground.core.interactor;

import com.wptdxii.playground.core.executor.PostExecutionThread;
import com.wptdxii.playground.core.executor.ThreadExecutor;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class SingleUseCase<Request, Response> {

    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mExecutionThread;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public SingleUseCase(ThreadExecutor threadExecutor, PostExecutionThread executionThread) {
        mThreadExecutor = threadExecutor;
        mExecutionThread = executionThread;
    }


    protected abstract Single<Response> buildUseCase(Request request);

    public void subscribe(Request request, DisposableSingleObserver<Response> observer) {
        DisposableSingleObserver mDisposable = buildUseCase(request)
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mExecutionThread.getScheduler())
                .subscribeWith(observer);
        mCompositeDisposable.add(mDisposable);
    }

    public void unsubscribe() {
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }
}
