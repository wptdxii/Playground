package com.wptdxii.playground.todo.tasks;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.wptdxii.playground.R;
import com.wptdxii.playground.base.BaseDrawerActivity;
import com.wptdxii.playground.todo.statistics.StatisticsActivity;

public class TasksActivity extends BaseDrawerActivity {
    @Override
    protected void setupToolbar(Toolbar toolbar) {
        toolbar.setTitle(R.string.todo_app_name);
    }

    @NonNull
    @Override
    protected Fragment onCreateFragment() {
        return TasksFragment.newInstance();
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
        nvDrawer.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.action_list:
                    // Do nothing, we're already on that screen
                    break;
                case R.id.action_statistics:
                    StatisticsActivity.start(this);
                    break;
                default:
                    break;
            }

            item.setChecked(true);
            toggleDrawer();
            return true;
        });
    }
}
