package com.wptdxii.playground.todo.tasks;

import com.wptdxii.playground.di.scope.FragmentScoped;
import com.wptdxii.playground.sample.dagger.ElectricHeater;
import com.wptdxii.playground.sample.dagger.Heater;
import com.wptdxii.playground.sample.dagger.Pump;
import com.wptdxii.playground.sample.dagger.Thermosiphon;
import com.wptdxii.playground.todo.tasks.TasksContract;
import com.wptdxii.playground.todo.tasks.TasksPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class TasksFragmentModule {

    @Binds
    abstract Pump privodePump(Thermosiphon thermosiphon);

    @Provides
    static Heater privodeHeater() {
        return new ElectricHeater();
    }

    @Binds
    @FragmentScoped
    abstract TasksContract.Presenter providePresenter(TasksPresenter presenter);
}
