package com.wptdxii.playground.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wptdxii.framekit.base.BaseFragment;
import com.wptdxii.playground.R;

/**
 * Created by wptdxii on 18-1-24
 */

public class DrawerFragment extends BaseFragment{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frgment_drawer, container, false);
    }

}
