package com.wptdxii.playground.sample.dagger;

import android.os.Bundle;
import android.util.Log;

import com.wptdxii.playground.R;
import com.wptdxii.playground.base.BaseDaggerActivity;

import javax.inject.Inject;

public class DaggerSampleActivity extends BaseDaggerActivity {

    private static final String TAG = "Coffee";

    @Inject
    CoffeeMaker mCoffeeMaker;

    @Inject
    CoffeeMaker mCoffeeMaker2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity_dagger_sample);

        mCoffeeMaker.brew();
        Log.e(TAG, "onCreate: " + mCoffeeMaker.toString());
        Log.e(TAG, "onCreate: " + mCoffeeMaker2.toString());
    }
}
