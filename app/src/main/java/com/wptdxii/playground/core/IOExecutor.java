package com.wptdxii.playground.core;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.FlowableTransformer;

@Singleton
public class IOExecutor<Response> implements Executor<Response> {

    @Inject
    IOExecutor() {

    }

    @Override
    public FlowableTransformer<Response, Response> transformer() {
        return TransformerProvider.ioTransformer();
    }
}
