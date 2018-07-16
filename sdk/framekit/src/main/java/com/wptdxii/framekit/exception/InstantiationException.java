package com.wptdxii.framekit.exception;

public final class InstantiationException extends UnsupportedOperationException {

    private static final String MSG_EXCEPTION = "Cannot be instantiated!";

    public InstantiationException() {
        super(MSG_EXCEPTION);
    }
}
