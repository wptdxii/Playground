package com.wptdxii.framekit.component.imageloader;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.View;

public class LoaderOptions {

    public String mUrl;
    public Uri mUri;
    @DrawableRes
    public int mDrawableResId = -1;

    public boolean asGif = false;
    public boolean asDrawable = true;

    public int mTargetWidth;
    public int mTargetHeight;

    @DrawableRes
    public int mPlaceHolderResId = -1;
    public ColorDrawable mPlaceHolderDrawable;
    @DrawableRes
    public int mErrorResId = -1;
    public ColorDrawable mErrorDrawable;
    @DrawableRes
    public int mFallbackResId = -1;
    public ColorDrawable mFallbackDrawable;
    public boolean mCenterCrop = false;
    public boolean mCenterInside = false;
    public boolean mFitCenter = false;
    public boolean mCircleCrop = false;
    public boolean mDontTransform = false;
    public boolean mCrossFade = false;
    public int mDuration = 0;

    public View mTartView;
    public LoadCallback mLoadCallback;

    public LoaderStrategy mLoaderStrategy;

    LoaderOptions(String url) {
        mUrl = url;
    }

    LoaderOptions(Uri uri) {
        mUri = uri;
    }

    LoaderOptions(@DrawableRes int drawableResId) {
        mDrawableResId = drawableResId;
    }

    public LoaderOptions asGif() {
        asGif = true;
        asDrawable = false;
        return this;
    }

    public LoaderOptions override(int width, int height) {
        mTargetWidth = width;
        mTargetHeight = height;
        return this;
    }

    public LoaderOptions override(int size) {
        mTargetWidth = size;
        mTargetHeight = size;
        return this;
    }

    public LoaderOptions placeHolder(@DrawableRes int placeHolderResId) {
        mPlaceHolderResId = placeHolderResId;
        mPlaceHolderDrawable = null;
        return this;
    }

    public LoaderOptions placeHolder(ColorDrawable placeHolderDrawable) {
        mPlaceHolderDrawable = placeHolderDrawable;
        mPlaceHolderResId = -1;
        return this;
    }

    public LoaderOptions error(@DrawableRes int errorResId) {
        mErrorResId = errorResId;
        mErrorDrawable = null;
        return this;
    }

    public LoaderOptions error(ColorDrawable errorDrawable) {
        mErrorDrawable = errorDrawable;
        mErrorResId = -1;
        return this;
    }

    public LoaderOptions fallback(@DrawableRes int fallbackResId) {
        mFallbackResId = fallbackResId;
        mFallbackDrawable = null;
        return this;
    }

    public LoaderOptions fallback(ColorDrawable fallbackDrawable) {
        mFallbackDrawable = fallbackDrawable;
        mFallbackResId = -1;
        return this;
    }

    public LoaderOptions centerCrop() {
        mCenterCrop = true;
        mCenterInside = false;
        mFitCenter = false;
        mCircleCrop = false;
        return this;
    }

    public LoaderOptions centerInside() {
        mCenterInside = true;
        mCenterCrop = false;
        mFitCenter = false;
        mCircleCrop = false;
        return this;
    }

    public LoaderOptions fitCenter() {
        mFitCenter = true;
        mCenterInside = false;
        mCenterCrop = false;
        mCircleCrop = false;
        return this;
    }

    public LoaderOptions circleCrop() {
        mCircleCrop = true;
        mCenterCrop = false;
        mCenterInside = false;
        mFitCenter = false;
        return this;
    }

    public LoaderOptions dontTransform() {
        mDontTransform = true;
        mCircleCrop = false;
        mCenterCrop = false;
        mCenterInside = false;
        mFitCenter = false;
        return this;
    }

    public LoaderOptions crossFade() {
        mCrossFade = true;
        return this;
    }

    public LoaderOptions crossFade(int duration) {
        mCrossFade = true;
        mDuration = duration;
        return this;
    }

    public LoaderOptions loader(LoaderStrategy loaderStrategy) {
        mLoaderStrategy = loaderStrategy;
        return this;
    }

    public void into(@NonNull View targetView) {
        mTartView = targetView;
        ImageLoader.get().loadOptions(this);
    }

    public void into(View tartView, @NonNull LoadCallback loadCallback) {
        mTartView = tartView;
        mLoadCallback = loadCallback;
        ImageLoader.get().loadOptions(this);
    }
}
