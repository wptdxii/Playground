package com.wptdxii.playground.todo.di;

import com.wptdxii.playground.di.scope.ActivityScoped;
import com.wptdxii.playground.di.scope.FragmentScoped;
import com.wptdxii.playground.todo.addedittask.AddEditActivity;
import com.wptdxii.playground.todo.addedittask.AddEditModule;
import com.wptdxii.playground.todo.statistics.StatisticsActivity;
import com.wptdxii.playground.todo.statistics.StatisticsModule;
import com.wptdxii.playground.todo.taskdetails.TaskDetailsActivity;
import com.wptdxii.playground.todo.taskdetails.TaskDetailsModule;
import com.wptdxii.playground.todo.tasks.TasksActivity;
import com.wptdxii.playground.todo.tasks.TasksFragment;
import com.wptdxii.playground.todo.tasks.TasksFragmentModule;
import com.wptdxii.playground.todo.tasks.TasksModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ToDoBindingModule {


    @ActivityScoped
    @ContributesAndroidInjector(modules = TasksModule.class)
    abstract TasksActivity tasksActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = TaskDetailsModule.class)
    abstract TaskDetailsActivity taskDetailsActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = StatisticsModule.class)
    abstract StatisticsActivity statisticsActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = AddEditModule.class)
    abstract AddEditActivity addEditActivity();

    @FragmentScoped
    @ContributesAndroidInjector(modules = TasksFragmentModule.class)
    abstract TasksFragment tasksFragment();
}
