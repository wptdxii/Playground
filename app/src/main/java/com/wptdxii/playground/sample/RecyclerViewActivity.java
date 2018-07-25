package com.wptdxii.playground.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wptdxii.framekit.base.BaseActivity;
import com.wptdxii.framekit.component.recyclerview.ItemViewBinder;
import com.wptdxii.framekit.component.recyclerview.TypeAdapter;
import com.wptdxii.playground.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
    }
}
