package com.wptdxii.playground.core.network.okhttp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.wptdxii.playground.BuildConfig;
import com.wptdxii.playground.core.network.okhttp.interceptor.HeaderInterceptor;
import com.wptdxii.playground.core.network.okhttp.interceptor.OfflineCacheInterceptor;
import com.wptdxii.playground.di.qualifier.AppContext;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by wptdxii on 2016/8/16 0016.
 * 单例模式
 */
public final class OkHttpManager {

    private static final String CACHE_PATH = "httpResponseCache";
    private static final int CACHE_SIZE = 10 * 1204 * 1024;
    private static final long TIMEOUT_READ = 15L;
    private static final long TIMEOUT_CONNECTION = 15L;
    private OkHttpClient mOkHttpClient;

    private volatile static OkHttpManager sInstance;


    public static OkHttpManager getInstance(Context context) {
        OkHttpManager result = sInstance;
        if (sInstance == null) {
            synchronized (OkHttpManager.class) {
                result = sInstance;
                if (result == null) {
                    sInstance = result = new OkHttpManager(context);
                }
            }
        }
        return result;
    }


    private OkHttpManager(@AppContext Context context) {
        if (sInstance != null) {
            throw new UnsupportedOperationException("Already instantiated");
        }
        init(context.getApplicationContext());
    }

    private void init(Context context) {
        HttpLoggingInterceptor loggingInterceptor = getLoggingInterceptor();
        Cache cache = getCacheDirectory(context);

        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new HeaderInterceptor())
                .addNetworkInterceptor(new OfflineCacheInterceptor(context))
                .addInterceptor(new OfflineCacheInterceptor(context))
                .retryOnConnectionFailure(true)
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .cache(cache)
                .build();
    }

    @NonNull
    private Cache getCacheDirectory(Context context) {
        File httpCacheDirectory = new File(context.getCacheDir(), CACHE_PATH);
        return new Cache(httpCacheDirectory, CACHE_SIZE);
    }

    @NonNull
    private HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ?
                HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return loggingInterceptor;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }
}
