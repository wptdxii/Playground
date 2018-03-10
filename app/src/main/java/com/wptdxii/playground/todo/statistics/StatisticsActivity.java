package com.wptdxii.playground.todo.statistics;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.wptdxii.framekit.base.BaseActivity;
import com.wptdxii.playground.R;

public class StatisticsActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, StatisticsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_activity_statistics);
    }
}
