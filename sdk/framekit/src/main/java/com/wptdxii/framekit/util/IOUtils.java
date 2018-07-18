package com.wptdxii.framekit.util;

import android.database.Cursor;

import java.io.Closeable;

public class IOUtils {

    private IOUtils() {
        throw new UnsupportedOperationException("Cannot be instantiated");
    }

    /**
     * Close the io stream
     *
     * @param closeable closeable
     */
    public static void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable e) {
                // ignore.
            }
        }
    }

    /**
     * Close the cursor
     *
     * @param cursor cursor
     */
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
