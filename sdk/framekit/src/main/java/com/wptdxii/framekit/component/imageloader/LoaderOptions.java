package com.wptdxii.framekit.component.imageloader;

import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.view.View;

import java.io.File;

public class LoaderOptions {

    // resource
    private String mUrl;
    private Uri mUri;
    @DrawableRes
    private int mDrawableResId;
    private File mFile;

    private int mTargetWidth;
    private int mTargetHeight;

    @DrawableRes
    private int mPlaceHolderResId;
    @DrawableRes
    private int mErrorResId;
    @DrawableRes
    private int mFallbackResId;

    private boolean mCenterCrop;
    private boolean mCenterInside;
    private boolean mFitCenter;
    private boolean mCircleCrop;
    private boolean mDontTransform;
    private boolean mCrossFade;
    private int mDuration;
    private boolean mSkipMemoryCache;


    private float mThumbnailSize;

    private int mRoundingRadius;

    private View mTartView;
    private LoadCallback mLoadCallback;

    private LoaderStrategy mLoaderStrategy;

    private LoaderOptions(Builder builder) {
        mUrl = builder.mUrl;
        mUri = builder.mUri;
        mDrawableResId = builder.mDrawableResId;
        mFile = builder.mFile;
        mTargetWidth = builder.mTargetWidth;
        mTargetHeight = builder.mTargetHeight;
        mPlaceHolderResId = builder.mPlaceHolderResId;
        mErrorResId = builder.mErrorResId;
        mFallbackResId = builder.mFallbackResId;
        mCenterCrop = builder.mCenterCrop;
        mCenterInside = builder.mCenterInside;
        mFitCenter = builder.mFitCenter;
        mCircleCrop = builder.mCircleCrop;
        mCrossFade = builder.mCrossFade;
        mDuration = builder.mDuration;
        mThumbnailSize = builder.mThumbnailSize;
        mDontTransform = builder.mDontTransform;
        mRoundingRadius = builder.mRoundingRadius;
        mSkipMemoryCache = builder.mSkipMemoryCache;
        mTartView = builder.mTartView;
        mLoadCallback = builder.mLoadCallback;
        mLoaderStrategy = builder.mLoaderStrategy;
    }

    public String getUrl() {
        return mUrl;
    }

    public Uri getUri() {
        return mUri;
    }

    public int getDrawableResId() {
        return mDrawableResId;
    }

    public File getFile() {
        return mFile;
    }

    public boolean isSkipMemoryCache() {
        return mSkipMemoryCache;
    }

    public float getThumbnailSize() {
        return mThumbnailSize;
    }

    public int getTargetWidth() {
        return mTargetWidth;
    }

    public int getTargetHeight() {
        return mTargetHeight;
    }

    public int getPlaceHolderResId() {
        return mPlaceHolderResId;
    }

    public int getErrorResId() {
        return mErrorResId;
    }

    public int getFallbackResId() {
        return mFallbackResId;
    }

    public boolean isCenterCrop() {
        return mCenterCrop;
    }

    public boolean isCenterInside() {
        return mCenterInside;
    }

    public boolean isFitCenter() {
        return mFitCenter;
    }

    public boolean isCircleCrop() {
        return mCircleCrop;
    }

    public boolean isCrossFade() {
        return mCrossFade;
    }

    public int getDuration() {
        return mDuration;
    }

    public boolean isDontTransform() {
        return mDontTransform;
    }

    public int getRoundingRadius() {
        return mRoundingRadius;
    }

    public View getTartView() {
        return mTartView;
    }

    public LoadCallback getLoadCallback() {
        return mLoadCallback;
    }

    public LoaderStrategy getLoaderStrategy() {
        return mLoaderStrategy;
    }

    public static final class Builder {

        // resource
        private String mUrl;
        private Uri mUri;
        @DrawableRes
        private int mDrawableResId = -1;
        private File mFile;

        // placeholders
        @DrawableRes
        private int mPlaceHolderResId = -1;
        @DrawableRes
        private int mErrorResId = -1;
        @DrawableRes
        private int mFallbackResId = -1;

        // request options
        private int mTargetWidth;
        private int mTargetHeight;
        private float mThumbnailSize = -1f;
        private boolean mSkipMemoryCache = false;

        // transformations
        private boolean mCenterCrop = false;
        private boolean mCenterInside = false;
        private boolean mFitCenter = false;
        private boolean mCircleCrop = false;
        private int mRoundingRadius = 0;
        private boolean mDontTransform = false;

        // transitions
        private boolean mCrossFade = false;
        private int mDuration = 0;

        private View mTartView;
        private LoadCallback mLoadCallback;

        private LoaderStrategy mLoaderStrategy;

        public Builder(String url) {
            mUrl = url;
        }

        public Builder(Uri uri) {
            mUri = uri;
        }

        public Builder(@DrawableRes int drawableResId) {
            mDrawableResId = drawableResId;
        }

        public Builder(File file) {
            mFile = file;
        }

        public Builder override(@IntRange(from = 0) int width, @IntRange(from = 0) int height) {
            mTargetWidth = width;
            mTargetHeight = height;
            return this;
        }

        public Builder override(@IntRange(from = 0) int size) {
            mTargetWidth = size;
            mTargetHeight = size;
            return this;
        }

        public Builder placeHolder(@DrawableRes int placeHolderResId) {
            mPlaceHolderResId = placeHolderResId;
            return this;
        }

        public Builder error(@DrawableRes int errorResId) {
            mErrorResId = errorResId;
            return this;
        }

        public Builder fallback(@DrawableRes int fallbackResId) {
            mFallbackResId = fallbackResId;
            return this;
        }

        public Builder centerCrop() {
            mCenterCrop = true;
            mCenterInside = false;
            mFitCenter = false;
            mCircleCrop = false;
            mRoundingRadius = 0;
            return this;
        }

        public Builder centerInside() {
            mCenterInside = true;
            mCenterCrop = false;
            mFitCenter = false;
            mCircleCrop = false;
            mRoundingRadius = 0;
            return this;
        }

        public Builder fitCenter() {
            mFitCenter = true;
            mCenterInside = false;
            mCenterCrop = false;
            mCircleCrop = false;
            mRoundingRadius = 0;
            return this;
        }

        public Builder thumbnail(@FloatRange(from = 0f, to = 1f) float thumbnailSize) {
            mThumbnailSize = thumbnailSize;
            return this;
        }

        public Builder skipMemoryCache() {
            mSkipMemoryCache = true;
            return this;
        }

        public Builder circleCrop() {
            mCircleCrop = true;
            mCenterCrop = false;
            mCenterInside = false;
            mFitCenter = false;
            mRoundingRadius = 0;
            return this;
        }

        public Builder dontTransform() {
            mDontTransform = true;
            mCircleCrop = false;
            mCenterCrop = false;
            mCenterInside = false;
            mFitCenter = false;
            mRoundingRadius = 0;
            return this;
        }

        public Builder crossFade() {
            mCrossFade = true;
            return this;
        }

        public Builder crossFade(int duration) {
            mCrossFade = true;
            mDuration = duration;
            return this;
        }

        public Builder roundingRadius(int roundingRadius) {
            mRoundingRadius = roundingRadius;
            return this;
        }

        public Builder loader(LoaderStrategy loaderStrategy) {
            mLoaderStrategy = loaderStrategy;
            return this;
        }

        public void into(@NonNull View targetView) {
            mTartView = targetView;
            ImageLoader.get().loadOptions(new LoaderOptions(this));
        }

        public void into(@NonNull View tartView, @NonNull LoadCallback loadCallback) {
            mTartView = tartView;
            mLoadCallback = loadCallback;
            ImageLoader.get().loadOptions(new LoaderOptions(this));
        }
    }
}
