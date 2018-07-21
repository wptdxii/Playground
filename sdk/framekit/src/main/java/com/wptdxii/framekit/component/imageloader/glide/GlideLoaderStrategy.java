package com.wptdxii.framekit.component.imageloader.glide;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.wptdxii.framekit.base.GlideApp;
import com.wptdxii.framekit.base.GlideRequest;
import com.wptdxii.framekit.base.GlideRequests;
import com.wptdxii.framekit.component.imageloader.LoadCallback;
import com.wptdxii.framekit.component.imageloader.LoaderOptions;
import com.wptdxii.framekit.component.imageloader.LoaderStrategy;

public class GlideLoaderStrategy implements LoaderStrategy {

    @Override
    public void load(LoaderOptions loaderOptions) {

        GlideRequests requests = GlideApp.with(loaderOptions.mTartView);

        if (loaderOptions.asGif) {
            GlideRequest<GifDrawable> gifRequest = requests.asGif();
            gifRequest = buildRequestResource(gifRequest, loaderOptions);
            gifRequest = gifRequest.apply(getRequestOptions(loaderOptions));
            if (loaderOptions.mTartView instanceof ImageView) {
                gifRequest.into((ImageView) loaderOptions.mTartView);
            } else {
                throw new IllegalArgumentException("TargetView should be ImageView!");
            }
        } else {
            GlideRequest<Drawable> drawableRequest = requests.asDrawable();
            drawableRequest = buildRequestResource(drawableRequest, loaderOptions);
            drawableRequest = drawableRequest.apply(getRequestOptions(loaderOptions));
            if (loaderOptions.mTartView instanceof ImageView) {
                drawableRequest.into((ImageView) loaderOptions.mTartView);
            } else {
                throw new IllegalArgumentException("TargetView should be ImageView!");
            }
        }
    }

    private void loadGifResource(LoaderOptions loaderOptions) {
        GlideRequests glideRequests = GlideApp.with(loaderOptions.mTartView);
        GlideRequest<GifDrawable> gifRequest = glideRequests.asGif();
    }

    private void loadDrawableResource(LoaderOptions loaderOptions) {
        GlideRequests glideRequests = GlideApp.with(loaderOptions.mTartView);
        GlideRequest<Drawable> drawableRequest = glideRequests.asDrawable();
        drawableRequest = buildRequestResource(drawableRequest, loaderOptions);
        if (loaderOptions.mCrossFade) {
            if (loaderOptions.mDuration <= 0) {
                drawableRequest = drawableRequest.transition(
                        DrawableTransitionOptions.withCrossFade());
            } else {
                drawableRequest = drawableRequest.transition(
                        DrawableTransitionOptions.withCrossFade(loaderOptions.mDuration));
            }
        }
        drawableRequest = drawableRequest.apply(getRequestOptions(loaderOptions));
        if (loaderOptions.mTartView != null && loaderOptions.mTartView instanceof ImageView) {
            drawableRequest.into((ImageView) loaderOptions.mTartView);
        } else {
        }
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

    private GlideRequest buildRequestResourceType(LoaderOptions loaderOptions) {
        GlideRequests glideRequests = GlideApp.with(loaderOptions.mTartView);
        GlideRequest glideRequest;
        if (loaderOptions.asGif) {
            glideRequest = glideRequests.asGif();
            return buildRequestResource(glideRequest, loaderOptions);
        } else {
            glideRequest = glideRequests.asDrawable();
            return buildRequestResource(glideRequest, loaderOptions);
        }
    }

    private <T> GlideRequest<T> buildRequestResource(GlideRequest<T> glideRequest, LoaderOptions loaderOptions) {
        if (loaderOptions.mUrl != null) {
            glideRequest = glideRequest.load(loaderOptions.mUrl);
        } else if (loaderOptions.mUri != null) {
            glideRequest = glideRequest.load(loaderOptions.mUri);
        } else if (loaderOptions.mDrawableResId != -1) {
            glideRequest = glideRequest.load(loaderOptions.mDrawableResId);
        }
        return glideRequest;
    }

    private final static class BitmapViewTarget extends ViewTarget<View, Bitmap> {

        private final LoadCallback mLoadCallback;

        public BitmapViewTarget(@NonNull View view, LoadCallback loadCallback) {
            super(view);
            mLoadCallback = loadCallback;
        }

        @Override
        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
            mLoadCallback.onResourceReady(resource);
        }
    }

}
