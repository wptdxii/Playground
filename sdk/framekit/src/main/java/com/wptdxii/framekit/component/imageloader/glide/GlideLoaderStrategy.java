package com.wptdxii.framekit.component.imageloader.glide;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.util.ViewPreloadSizeProvider;
import com.wptdxii.framekit.component.imageloader.LoadCallback;
import com.wptdxii.framekit.component.imageloader.LoaderOptions;
import com.wptdxii.framekit.component.imageloader.LoaderStrategy;
import com.wptdxii.framekit.util.Preconditions;

public class GlideLoaderStrategy implements LoaderStrategy {

    @Override
    public void load(LoaderOptions loaderOptions) {
        Preconditions.checkNotNull(loaderOptions.getTartView(), "Target view cannot be empty!");
        GlideRequests requests = GlideApp.with(loaderOptions.getTartView().getContext());
        GlideRequest<Drawable> drawableRequest = requests.asDrawable();
        drawableRequest = applyRequestResource(drawableRequest, loaderOptions)
                .apply(applyRequestOptions(loaderOptions))
                .transition(applyDrawableTransitions(loaderOptions));
        float thumbnailSize = loaderOptions.getThumbnailSize();
        if (thumbnailSize >= 0f && thumbnailSize <= 1f) {
            drawableRequest = drawableRequest.thumbnail(loaderOptions.getThumbnailSize());
        }

        applyTargetView(loaderOptions, drawableRequest);
    }

    private void applyTargetView(LoaderOptions loaderOptions, GlideRequest<Drawable> drawableRequest) {
        View targetView = loaderOptions.getTartView();
        if (targetView instanceof ImageView) {
            drawableRequest.into((ImageView) loaderOptions.getTartView());
        } else if (loaderOptions.getLoadCallback() != null) {
            drawableRequest.into(new DrawableViewTarget(targetView, loaderOptions.getLoadCallback()));
        } else {
            throw new IllegalArgumentException("Target view should be ImageView or LoadingCallback not empty");
        }
    }

    private TransitionOptions<?, ? super Drawable> applyDrawableTransitions(LoaderOptions loaderOptions) {
        DrawableTransitionOptions transitionOptions = new DrawableTransitionOptions();
        if (loaderOptions.isCrossFade()) {
            int duration = loaderOptions.getDuration();
            transitionOptions = duration > 0 ?
                    transitionOptions.crossFade(duration) : transitionOptions.crossFade();
        }
        return transitionOptions;
    }

    private RequestOptions applyRequestOptions(LoaderOptions loaderOptions) {
        RequestOptions requestOptions = new RequestOptions()
                .skipMemoryCache(loaderOptions.isSkipMemoryCache());

        int targetWidth = loaderOptions.getTargetWidth();
        int targetHeight = loaderOptions.getTargetHeight();
        if (targetWidth > 0 && targetHeight > 0) {
            requestOptions = requestOptions.override(targetWidth, targetHeight);
        }
        requestOptions = applyPlaceHolders(loaderOptions, requestOptions);
        requestOptions = applyTransformations(loaderOptions, requestOptions);
        return requestOptions;
    }

    private RequestOptions applyTransformations(LoaderOptions loaderOptions, RequestOptions requestOptions) {
        if (loaderOptions.isDontTransform()) {
            return requestOptions.dontTransform();
        }

        if (loaderOptions.isCenterCrop()) {
            requestOptions = requestOptions.centerCrop();
        } else if (loaderOptions.isCenterInside()) {
            requestOptions = requestOptions.centerInside();
        } else if (loaderOptions.isFitCenter()) {
            requestOptions = requestOptions.fitCenter();
        } else if (loaderOptions.isCircleCrop()) {
            requestOptions = requestOptions.circleCrop();
        } else if (loaderOptions.getRoundingRadius() > 0) {
            requestOptions = requestOptions.transform(
                    new RoundedCorners(loaderOptions.getRoundingRadius()));
        }
        return requestOptions;
    }

    private RequestOptions applyPlaceHolders(LoaderOptions loaderOptions, RequestOptions requestOptions) {
        if (loaderOptions.getPlaceHolderResId() != -1) {
            requestOptions = requestOptions.placeholder(loaderOptions.getDrawableResId());
        }

        if (loaderOptions.getErrorResId() != -1) {
            requestOptions = requestOptions.error(loaderOptions.getErrorResId());
        }

        if (loaderOptions.getFallbackResId() != -1) {
            requestOptions = requestOptions.fallback(loaderOptions.getFallbackResId());
        }
        return requestOptions;
    }

    private <T> GlideRequest<T> applyRequestResource(GlideRequest<T> glideRequest,
                                                     LoaderOptions loaderOptions) {
        if (loaderOptions.getUrl() != null) {
            glideRequest = glideRequest.load(loaderOptions.getUrl());
        } else if (loaderOptions.getUri() != null) {
            glideRequest = glideRequest.load(loaderOptions.getUri());
        } else if (loaderOptions.getDrawableResId() != -1) {
            glideRequest = glideRequest.load(loaderOptions.getDrawableResId());
        } else if (loaderOptions.getFile() != null) {
            glideRequest = glideRequest.load(loaderOptions.getFile());
        }

        return glideRequest;
    }

    private final static class DrawableViewTarget extends
            com.bumptech.glide.request.target.ViewTarget<View, Drawable> {

        private final LoadCallback mLoadCallback;

        DrawableViewTarget(@NonNull View view, LoadCallback loadCallback) {
            super(view);
            mLoadCallback = loadCallback;
        }

        @Override
        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
            mLoadCallback.onResourceReady(resource);
        }
    }
}
