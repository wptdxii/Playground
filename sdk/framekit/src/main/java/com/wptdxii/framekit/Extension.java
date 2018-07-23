package com.wptdxii.framekit;

import android.app.Application;

import com.wptdxii.framekit.util.Preconditions;

public final class Extension {

    private final boolean mBuildType;
    private final Application mApplication;

    private volatile static Extension sExtension;

    public static Extension get() {
        Preconditions.checkNotNull(sExtension);
        return sExtension;
    }

    public boolean isBuildTypeDebug() {
        Preconditions.checkNotNull(mBuildType);
        return mBuildType;
    }

    public Application getApplication() {
        Preconditions.checkNotNull(mApplication);
        return mApplication;
    }

    /**
     * should only instantiated once
     *
     * @param extension
     */
    public static void install(Extension extension) {
        Extension result = sExtension;
        if (result == null) {
            synchronized (Extension.class) {
                result = sExtension;
                if (result == null) {
                    sExtension = extension;
                }
            }
        }
    }

    private Extension(Builder builder) {
        mApplication = builder.mApplication;
        mBuildType = builder.mBuildType;
    }

    public static class Builder {
        private Application mApplication;
        private boolean mBuildType = BuildConfig.DEBUG;

        public Builder buildType(boolean buildType) {
            mBuildType = buildType;
            return this;
        }

        public Builder application(Application application) {
            this.mApplication = application;
            return this;
        }

        public Extension build() {
            return new Extension(this);
        }
    }
}
