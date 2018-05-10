package com.wptdxii.playground.todo.todo_mvp.statistics;

import android.support.annotation.NonNull;
import android.util.Log;

import com.wptdxii.playground.todo.todo_mvp.data.Task;
import com.wptdxii.playground.todo.todo_mvp.data.source.TasksDataSource;
import com.wptdxii.playground.todo.todo_mvp.data.source.TasksRepository;

import java.util.List;

public class StatisticsPresenter implements StatisticsContract.Presenter {

    private StatisticsContract.View mStatisticsView;
    private TasksRepository mTasksRepository;

    public StatisticsPresenter(TasksRepository repository) {
        mTasksRepository = repository;
    }

    @Override
    public void attach(StatisticsContract.View view) {
        mStatisticsView = view;
    }

    @Override
    public void detach() {
        mStatisticsView = null;
    }

    private static final String TAG = "StatisticsPresenter";
    @Override
    public void getTasksStatistics() {
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
                Log.e(TAG, "onDataNotAvailable: _--------" );
                if (mStatisticsView != null) {
                    mStatisticsView.showLoadingIndicator(false);
                    mStatisticsView.showLoadingStatisticsError();
                }
            }
        });
    }
}
