package com.wptdxii.playground.todo.tasks;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wptdxii.framekit.base.BaseFragment;
import com.wptdxii.playground.R;

import butterknife.ButterKnife;

/**
 * Created by wptdxii on 2018/1/25 0025 18:01
 */

public class TasksFragment extends BaseFragment {

    public static Fragment newInstance() {
        return new TasksFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
