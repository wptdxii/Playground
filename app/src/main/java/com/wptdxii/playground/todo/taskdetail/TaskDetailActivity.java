package com.wptdxii.playground.todo.taskdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.wptdxii.framekit.base.BaseActivity;
import com.wptdxii.playground.R;

public class TaskDetailActivity extends BaseActivity {
    public static final String EXTRA_TASK_ID = "extra_task_id";

    public static void start(@NonNull Context context, @NonNull String taskId) {
        Intent intent = new Intent(context, TaskDetailActivity.class);
        intent.putExtra(EXTRA_TASK_ID, taskId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_activity_task_detail);
    }
}
