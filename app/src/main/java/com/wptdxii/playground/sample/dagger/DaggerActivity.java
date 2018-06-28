package com.wptdxii.playground.sample.dagger;

import android.os.Bundle;
import android.util.Log;

import com.wptdxii.framekit.base.BaseActivity;
import com.wptdxii.playground.R;
import com.wptdxii.playground.di.component.DaggerAppComponent;
import com.wptdxii.playground.sample.dagger.di.component.DaggerCoffeeShopComponent;

import javax.inject.Inject;
import javax.inject.Provider;

import dagger.Lazy;

public class DaggerActivity extends BaseActivity {

    private static final String TAG = "Coffee";


    @Inject
    Lazy<CoffeeMaker> mCoffeeMakerLazy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity_dagger);

        DaggerCoffeeShopComponent.create().inject(this);

        mCoffeeMakerLazy.get().brew();

        Log.e(TAG, "onCreate: " + mCoffeeMakerLazy.get().toString());
        Log.e(TAG, "onCreate: " + mCoffeeMakerLazy.get().toString());
    }
}
