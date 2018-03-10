package com.wptdxii.playground.todo.addnewtask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.wptdxii.framekit.base.BaseActivity;
import com.wptdxii.playground.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNewTaskActivity extends BaseActivity {

    public static final int REQUEST_CODE_ADD_TASK = 1;
    public static final String EXTRA_TASK_ID = "extra_task_id";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_new_task_title)
    EditText etNewTaskTitle;
    @BindView(R.id.et_new_task_description)
    EditText etNewTaskDescription;

    public static void startFortResult(Activity activity) {
        Intent intent = new Intent(activity, AddNewTaskActivity.class);
        activity.startActivityForResult(intent, REQUEST_CODE_ADD_TASK);
    }

    public static void starForResult(Activity activity, @Nullable String taskId) {
        if (taskId == null) {
            startFortResult(activity);
        } else {
            Intent intent = new Intent(activity, AddNewTaskActivity.class);
            intent.putExtra(EXTRA_TASK_ID, taskId);
            activity.startActivityForResult(intent, REQUEST_CODE_ADD_TASK);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_activity_add_new_task);
        ButterKnife.bind(this);

        setupToolbar(toolbar);
        setupActionBar();
    }

    private void setupActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupToolbar(Toolbar toolbar) {
        String taskId = getIntent().getStringExtra(EXTRA_TASK_ID);
        toolbar.setTitle(taskId == null ?
                getString(R.string.todo_add_new_task_activity_title_add) :
                getString(R.string.todo_add_new_task_activity_title_edit));
    }
}
