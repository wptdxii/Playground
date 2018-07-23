package com.wptdxii.framekit.component.imageloader.glide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.wptdxii.framekit.Extension;

@com.bumptech.glide.annotation.GlideModule
public class AppGlide extends AppGlideModule {
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        if (Extension.get().isBuildTypeDebug()) {
            builder.setLogLevel(Log.DEBUG);
        } else {
            builder.setLogLevel(Log.ERROR);
        }

        builder.setDefaultRequestOptions(new RequestOptions()
                .format(DecodeFormat.PREFER_ARGB_8888)
        );
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
