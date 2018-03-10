package com.wptdxii.framekit.util;

import android.support.annotation.Nullable;

public final class Preconditions {

    private Preconditions() {
        throw new UnsupportedOperationException("Cannot be instantiated!");
    }

    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        } else {
            return reference;
        }
    }
}
