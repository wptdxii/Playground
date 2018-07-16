package com.wptdxii.framekit.util;

import android.database.Cursor;

import java.io.Closeable;

public class IoUtils {

    private IoUtils() {
        throw new UnsupportedOperationException("Cannot be instantiated");
    }

    public static void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable e) {
                // ignore.
            }
        }
    }

    public static void closeSilently(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            try {
                cursor.close();
            } catch (Throwable e) {
                // ignore.
            }
        }
    }
}
