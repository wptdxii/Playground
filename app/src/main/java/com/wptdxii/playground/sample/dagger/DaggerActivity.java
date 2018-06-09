package com.wptdxii.playground.sample.dagger;

import android.os.Bundle;

import com.wptdxii.framekit.base.BaseActivity;
import com.wptdxii.playground.R;
import com.wptdxii.playground.sample.dagger.di.component.DaggerCoffeeShopComponent;

import javax.inject.Inject;

public class DaggerActivity extends BaseActivity {

    @Inject
    CoffeeMaker mCoffeeMaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity_dagger);

        DaggerCoffeeShopComponent.create().inject(this);

        mCoffeeMaker.brew();
    }
}
