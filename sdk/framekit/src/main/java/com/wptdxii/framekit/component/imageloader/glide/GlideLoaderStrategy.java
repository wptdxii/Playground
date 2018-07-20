package com.wptdxii.framekit.component.imageloader.glide;

import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.wptdxii.framekit.base.GlideApp;
import com.wptdxii.framekit.base.GlideRequest;
import com.wptdxii.framekit.base.GlideRequests;
import com.wptdxii.framekit.component.imageloader.LoaderOptions;
import com.wptdxii.framekit.component.imageloader.LoaderStrategy;

public class GlideLoaderStrategy implements LoaderStrategy {

    @Override
    public void load(LoaderOptions loaderOptions) {
        buildRequestSourceType(loaderOptions)
                .apply(getRequestOptions(loaderOptions))
                .into((ImageView) loaderOptions.mTartView);
    }

    private RequestOptions getRequestOptions(LoaderOptions loaderOptions) {
        RequestOptions requestOptions = new RequestOptions();
        if (loaderOptions.mPlaceHolderResId != -1) {
            requestOptions = requestOptions.placeholder(loaderOptions.mPlaceHolderResId);
        }
        if (loaderOptions.mPlaceHolderDrawable != null) {
            requestOptions = requestOptions.placeholder(loaderOptions.mPlaceHolderDrawable);
        }

        if (loaderOptions.mErrorResId != -1) {
            requestOptions = requestOptions.error(loaderOptions.mErrorResId);
        }
        if (loaderOptions.mErrorDrawable != null) {
            requestOptions = requestOptions.error(loaderOptions.mErrorDrawable);
        }

        if (loaderOptions.mFallbackResId != -1) {
            requestOptions = requestOptions.fallback(loaderOptions.mFallbackResId);
        }
        if (loaderOptions.mFallbackDrawable != null) {
            requestOptions = requestOptions.fallback(loaderOptions.mFallbackDrawable);
        }

        if (loaderOptions.mCenterCrop) {
            requestOptions = requestOptions.centerCrop();
        }
        if (loaderOptions.mCenterInside) {
            requestOptions = requestOptions.centerInside();
        }
        if (loaderOptions.mFitCenter) {
            requestOptions = requestOptions.fitCenter();
        }
        if (loaderOptions.mCircleCrop) {
            requestOptions = requestOptions.circleCrop();
        }

        return requestOptions;
    }

    private GlideRequest buildRequestSourceType(LoaderOptions loaderOptions) {
        GlideRequests glideRequests = GlideApp.with(loaderOptions.mTartView);
        GlideRequest glideRequest;
        if (loaderOptions.asGif) {
            glideRequest = glideRequests.asGif();
            return buildRequestSource(glideRequest, loaderOptions);
        } else if (loaderOptions.asBitmap) {
            glideRequest = glideRequests.asBitmap();
            return buildRequestSource(glideRequest, loaderOptions);
        } else {
            glideRequest = glideRequests.asDrawable();
            return buildRequestSource(glideRequest, loaderOptions);
        }
    }

    private GlideRequest buildRequestSource(GlideRequest glideRequest, LoaderOptions loaderOptions) {
        if (loaderOptions.mUrl != null) {
            glideRequest = glideRequest.load(loaderOptions.mUrl);
        } else if (loaderOptions.mUri != null) {
            glideRequest = glideRequest.load(loaderOptions.mUri);
        } else if (loaderOptions.mDrawableResId != -1) {
            glideRequest = glideRequest.load(loaderOptions.mDrawableResId);
        }
        return glideRequest;
    }
}
