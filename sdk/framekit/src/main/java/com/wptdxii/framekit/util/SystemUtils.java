package com.wptdxii.framekit.util;

import android.content.Intent;

import com.wptdxii.framekit.Extension;
import com.wptdxii.framekit.exception.InstantiationException;

public final class SystemUtils {

    private SystemUtils() {
        throw new InstantiationException();
    }


    /**
     * Open the settings of wireless.
     */
    public static void openWirelessSettings() {
        Extension.get().getApplication().startActivity(
                new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        );
    }
}
