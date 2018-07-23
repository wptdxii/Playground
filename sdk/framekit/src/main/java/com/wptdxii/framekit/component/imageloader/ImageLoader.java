package com.wptdxii.framekit.component.imageloader;

import android.net.Uri;
import android.support.annotation.DrawableRes;

import com.wptdxii.framekit.exception.InstantiatedException;
import com.wptdxii.framekit.util.Preconditions;

import java.io.File;

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

    public LoaderOptions.Builder load(String url) {
        return new LoaderOptions.Builder(url);
    }

    public LoaderOptions.Builder load(Uri uri) {
        return new LoaderOptions.Builder(uri);
    }

    public LoaderOptions.Builder load(@DrawableRes int drawableResId) {
        return new LoaderOptions.Builder(drawableResId);
    }

    public LoaderOptions.Builder load(File file) {
        return new LoaderOptions.Builder(file);
    }

    public void loadOptions(LoaderOptions loaderOptions) {
        LoaderStrategy loaderStrategy = loaderOptions.getLoaderStrategy();
        if (loaderStrategy != null) {
            loaderStrategy.load(loaderOptions);
        } else {
            Preconditions.checkNotNull(mLoaderStrategy, "Loader strategy cannot be empty!");
            mLoaderStrategy.load(loaderOptions);
        }
    }
}
