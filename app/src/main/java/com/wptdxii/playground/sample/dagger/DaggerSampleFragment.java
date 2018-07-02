package com.wptdxii.playground.sample.dagger;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wptdxii.playground.R;
import com.wptdxii.playground.base.BaseFragment;

import javax.inject.Inject;

public class DaggerSampleFragment extends BaseFragment {

    private static final String TAG = "Dagger";

    @Inject
    CoffeeMaker mCoffeeMaker;

    public static DaggerSampleFragment newInstance() {
        return new DaggerSampleFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCoffeeMaker.brew();
        Log.e(TAG, "DaggerSampleFragment: " + mCoffeeMaker);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sample_fragment_dagger_sample, container, false);
    }
}
