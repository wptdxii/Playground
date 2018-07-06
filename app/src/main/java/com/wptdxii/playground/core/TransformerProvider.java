package com.wptdxii.playground.core;

import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TransformerProvider {

    private TransformerProvider() {
    }

    private static final FlowableTransformer ioTransformer =
            upstream -> upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

    private static final FlowableTransformer newThreadTransformer =
            upstream -> upstream.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread());

    private static final FlowableTransformer computationTransformer =
            upstream -> upstream.subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread());

    private static final FlowableTransformer trampolineTransformer =
            upstream -> upstream.subscribeOn(Schedulers.trampoline())
                    .observeOn(AndroidSchedulers.mainThread());

    @SuppressWarnings("unchecked")
    public static <Response> FlowableTransformer<Response, Response> ioTransformer() {
        return (FlowableTransformer<Response, Response>) ioTransformer;
    }

    @SuppressWarnings("unchecked")
    public static <Response> FlowableTransformer<Response, Response> computationTransformer() {
        return (FlowableTransformer<Response, Response>) computationTransformer;
    }

    @SuppressWarnings("unchecked")
    public static <Response> FlowableTransformer<Response, Response> newThreadTransformer() {
        return (FlowableTransformer<Response, Response>) newThreadTransformer;
    }

    @SuppressWarnings("unchecked")
    public static <Response> FlowableTransformer<Response, Response> trampolineTransformer() {
        return (FlowableTransformer<Response, Response>) trampolineTransformer;
    }
}
