package com.wptdxii.playground.core.executor;

import io.reactivex.FlowableTransformer;

public interface Executor<Response> {

    FlowableTransformer<Response, Response> transformer();

}
