package com.wptdxii.playground.todo.tasks;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wptdxii.framekit.base.BaseFragment;
import com.wptdxii.playground.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wptdxii on 2018/1/25 0025 18:01
 */

public class TasksFragment extends BaseFragment {
    @BindView(R.id.tv_filtering_label)
    TextView tvFilteringLabel;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.group_list)
    Group groupList;
    @BindView(R.id.tv_no_tasks)
    TextView tvNoTasks;
    @BindView(R.id.iv_no_tasks)
    ImageView ivNoTasks;
    @BindView(R.id.tv_no_tasks_add)
    TextView tvNoTasksAdd;
    @BindView(R.id.group_no_tasks)
    Group groupNoTasks;
    @BindView(R.id.cl_content)
    ConstraintLayout clContent;
    @BindView(R.id.fab_add_task)
    FloatingActionButton fabAddTask;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;

    public static Fragment newInstance() {
        return new TasksFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
