package com.wptdxii.playground.core;

import io.reactivex.FlowableTransformer;

public interface Executor<Response> {

    FlowableTransformer<Response, Response> transformer();

}
