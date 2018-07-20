package com.wptdxii.framekit.component.imageloader;

import android.net.Uri;
import android.support.annotation.DrawableRes;

import com.wptdxii.framekit.exception.InstantiatedException;
import com.wptdxii.framekit.util.Preconditions;

public class ImageLoader {

    private LoaderStrategy mLoaderStrategy;

    private ImageLoader() {
        if (SingletonHolder.INSTANCE != null) {
            throw new InstantiatedException();
        }
    }


    private static class SingletonHolder {
        private static final ImageLoader INSTANCE = new ImageLoader();
    }

    public static ImageLoader get() {
        return SingletonHolder.INSTANCE;
    }

    public void setLoaderStrategy(LoaderStrategy loaderStrategy) {
        mLoaderStrategy = loaderStrategy;
    }

    public LoaderOptions load(String url) {
        return new LoaderOptions(url);
    }

    public LoaderOptions load(Uri uri) {
        return new LoaderOptions(uri);
    }

    public LoaderOptions load(@DrawableRes int drawableResId) {
        return new LoaderOptions(drawableResId);
    }

    public void loadOptions(LoaderOptions loaderOptions) {
        LoaderStrategy loaderStrategy = loaderOptions.mLoaderStrategy;
        if (loaderStrategy != null) {
            loaderStrategy.load(loaderOptions);
        } else {
            Preconditions.checkNotNull(mLoaderStrategy);
            mLoaderStrategy.load(loaderOptions);
        }
    }

}
