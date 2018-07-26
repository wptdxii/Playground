package com.wptdxii.playground.sample.recyclerview;

import android.support.annotation.DrawableRes;

public class Banner {
    private int bannerResId;

    public Banner(@DrawableRes int bannerResId) {
        this.bannerResId = bannerResId;
    }

    public int getBannerResId() {
        return bannerResId;
    }
}
