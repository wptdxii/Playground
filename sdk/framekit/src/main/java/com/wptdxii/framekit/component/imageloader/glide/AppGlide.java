package com.wptdxii.framekit.component.imageloader.glide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.module.AppGlideModule;

@com.bumptech.glide.annotation.GlideModule
public class AppGlide extends AppGlideModule {
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        builder.setLogLevel(Log.DEBUG);
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
