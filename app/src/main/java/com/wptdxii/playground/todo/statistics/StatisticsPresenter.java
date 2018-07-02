package com.wptdxii.playground.todo.statistics;

import android.support.annotation.NonNull;
import android.util.Log;

import com.wptdxii.playground.di.scope.ActivityScoped;
import com.wptdxii.playground.di.scope.FragmentScoped;
import com.wptdxii.playground.todo.data.source.Task;
import com.wptdxii.playground.todo.data.source.TasksDataSource;
import com.wptdxii.playground.todo.data.TasksRepository;

import java.util.List;

import javax.inject.Inject;

final class StatisticsPresenter implements StatisticsContract.Presenter {

    private StatisticsContract.View mStatisticsView;
    private TasksRepository mTasksRepository;

    @Inject
    StatisticsPresenter(TasksRepository repository) {
        mTasksRepository = repository;
    }

    @Override
    public void attach(StatisticsContract.View view) {
        mStatisticsView = view;
        getTasksStatistics();
    }

    @Override
    public void detach() {
        mStatisticsView = null;
    }

    private static final String TAG = "StatisticsPresenter";

    private void getTasksStatistics() {
        if (mStatisticsView != null) {
            mStatisticsView.showLoadingIndicator(true);
        }

        mTasksRepository.getTasks(new TasksDataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(@NonNull List<Task> tasks) {
                Log.e(TAG, "onTasksLoaded: +++++");
                int activeTasks = 0;
                int completedTasks = 0;

                for (Task task : tasks) {

                    if (task.isCompleted()) {
                        completedTasks += 1;
                    } else {
                        activeTasks += 1;
                    }
                }

                if (mStatisticsView != null) {
                    mStatisticsView.showLoadingIndicator(false);
                    mStatisticsView.showTasksStatistics(String.valueOf(activeTasks), String.valueOf(completedTasks));
                }
            }

            @Override
            public void onDataNotAvailable() {
                Log.e(TAG, "onDataNotAvailable: _--------");
                if (mStatisticsView != null) {
                    mStatisticsView.showLoadingIndicator(false);
                    mStatisticsView.showLoadingStatisticsError();
                }
            }
        });
    }
}
