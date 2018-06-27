package com.wptdxii.playground.sample.dagger;

import android.os.Bundle;
import android.util.Log;

import com.wptdxii.framekit.base.BaseActivity;
import com.wptdxii.playground.R;
import com.wptdxii.playground.sample.dagger.di.component.DaggerCoffeeShopComponent;

import javax.inject.Inject;

public class DaggerActivity extends BaseActivity {

    private static final String TAG = "DaggerActivity";

    @Inject
    CoffeeMaker mCoffeeMaker;

    @Inject
    CoffeeMaker mMaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity_dagger);

        DaggerCoffeeShopComponent.create().inject(this);

        mCoffeeMaker.brew();

        Log.e(TAG, "onCreate: " + mCoffeeMaker.toString());
        Log.e(TAG, "onCreate: " + mMaker.toString());
    }
}
