package com.wptdxii.playground.todo.tasks;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.wptdxii.framekit.base.BaseActivity;
import com.wptdxii.playground.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TasksActivity extends BaseActivity {
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        ButterKnife.bind(this);

        swipeRefreshLayout.setColorSchemeResources(R.color.black, R.color.pink_light);
        swipeRefreshLayout.setOnRefreshListener(() ->
                Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show());
    }
}
