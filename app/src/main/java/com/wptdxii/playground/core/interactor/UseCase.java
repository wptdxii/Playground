package com.wptdxii.playground.core.interactor;

public interface UseCase<Request, Response, Subscriber> {

     buildUseCase(Request request);

    void subscribe(P params);

    void unsubscribe();

    interface Reqeust {
    }
}
