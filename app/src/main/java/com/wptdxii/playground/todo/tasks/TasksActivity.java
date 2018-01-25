package com.wptdxii.playground.todo.tasks;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.wptdxii.playground.R;
import com.wptdxii.playground.base.BaseDrawerActivity;

public class TasksActivity extends BaseDrawerActivity {
    @Override
    protected void setupToolbar(Toolbar toolbar) {

    }

    @NonNull
    @Override
    protected Fragment onCreateFragment() {
        return new TasksFragment();
    }

    @Override
    protected int onCreateNavigationHeader() {
        return R.layout.todo_activity_tasks_nav_header;
    }

    @Override
    protected int onCreateNavigationMenu() {
        return R.menu.todo_activity_tasks_drawer;
    }

    @Override
    protected void setupNavigationView(NavigationView nvDrawer) {

    }
}
