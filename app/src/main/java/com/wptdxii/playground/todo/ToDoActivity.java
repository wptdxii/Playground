package com.wptdxii.playground.todo;

import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;

import com.wptdxii.playground.R;
import com.wptdxii.playground.base.BaseListActivity;
import com.wptdxii.playground.base.Module;
import com.wptdxii.playground.todo.todo_mvp.tasks.TasksActivity;

import java.util.ArrayList;
import java.util.List;

public class ToDoActivity extends BaseListActivity {

    @Override
    protected void setupToolbar(Toolbar toolbar) {
        toolbar.setTitle(getString(R.string.module_to_do));
    }

    @NonNull
    @Override
    protected List<Module> createModules() {
        List<Module> modules = new ArrayList<>();
        modules.add(new Module("To-Do-MVP", TasksActivity.class));
        return modules;
    }
}
