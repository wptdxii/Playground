package com.wptdxii.playground.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wptdxii.playground.base.BaseDaggerFragment;
import com.wptdxii.playground.base.BaseFragment;
import com.wptdxii.playground.R;
import com.wptdxii.playground.di.scope.ActivityScoped;
import com.wptdxii.playground.sample.dagger.CoffeeMaker;

import javax.inject.Inject;

/**
 * Created by wptdxii on 18-1-24
 */

@ActivityScoped
public class DrawerFragment extends BaseDaggerFragment {

    //    @Inject
    //    CoffeeMaker mCoffeeMaker;

    @Inject
    public DrawerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frgment_drawer, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        //        Log.e(TAG, "onResume: " + mCoffeeMaker.toString());
    }

    private static final String TAG = "DrawerFragment";
}
