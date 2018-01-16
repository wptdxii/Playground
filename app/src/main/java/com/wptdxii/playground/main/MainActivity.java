package com.wptdxii.playground.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.wptdxii.framekit.base.BaseActivity;
import com.wptdxii.playground.R;

public class MainActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
