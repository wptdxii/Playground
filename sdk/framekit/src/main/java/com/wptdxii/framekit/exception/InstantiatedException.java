package com.wptdxii.framekit.exception;

public final class InstantiatedException extends UnsupportedOperationException {

    private static final String MSG_EXCEPTION = "Already been instantiated!";

    public InstantiatedException() {
        super(MSG_EXCEPTION);
    }
}
