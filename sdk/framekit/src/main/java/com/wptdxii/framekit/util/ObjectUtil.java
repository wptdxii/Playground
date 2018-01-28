package com.wptdxii.framekit.util;

import android.support.annotation.Nullable;

/**
 * Created by wptdxii on 2018/1/26 0026 18:02
 */

public class ObjectUtil {

    public static boolean equals(@Nullable Object a, @Nullable Object b) {
        return a == b || (a != null && a.equals(b));
    }
}
