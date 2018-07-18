package com.wptdxii.framekit.util;

import android.annotation.SuppressLint;
import android.support.v7.view.menu.MenuBuilder;
import android.view.Menu;

import com.wptdxii.framekit.exception.InstantiationException;

public final class MenuUtil {
    private MenuUtil() {
        throw new InstantiationException();
    }

    public static void showOptionalIcons(Menu menu) {
        setOptionalIconsVisible(menu, true);
    }


    public static void hideOptionalIcons(Menu menu) {
        setOptionalIconsVisible(menu, false);
    }

    @SuppressLint("RestrictedApi")
    private static void setOptionalIconsVisible(Menu menu, boolean visible) {
        if (menu instanceof MenuBuilder) {
            ((MenuBuilder) menu).setOptionalIconsVisible(visible);
        }
    }
}
