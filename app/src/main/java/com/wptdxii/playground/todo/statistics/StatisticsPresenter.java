package com.wptdxii.playground.todo.statistics;

import android.support.annotation.NonNull;
import android.util.Pair;

import com.wptdxii.playground.core.schedulers.ISchedulerProvider;
import com.wptdxii.playground.todo.data.source.Task;
import com.wptdxii.playground.todo.data.TasksRepository;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

final class StatisticsPresenter implements StatisticsContract.Presenter {

    private StatisticsContract.View mStatisticsView;
    private final TasksRepository mTasksRepository;
    private final CompositeDisposable mCompositeDisposable;
    private final ISchedulerProvider mSchedulerProvider;

    @Inject
    StatisticsPresenter(@NonNull TasksRepository repository,
                        @NonNull CompositeDisposable compositeDisposable,
                        @NonNull ISchedulerProvider schedulerProvider) {
        mTasksRepository = repository;
        mCompositeDisposable = compositeDisposable;
        mSchedulerProvider = schedulerProvider;
    }

    @Override
    public void attach(StatisticsContract.View view) {
        mStatisticsView = view;
        getTasksStatistics();
    }

    @Override
    public void detach() {
        mStatisticsView = null;
        mCompositeDisposable.clear();
    }

    private void getTasksStatistics() {
        showIndicator(true);

        Flowable<Task> tasksFlowable = mTasksRepository
                .getTasks()
                .flatMap(Flowable::fromIterable);

        Flowable<Long> completedTasksCount = tasksFlowable
                .filter(Task::isCompleted)
                .count()
                .toFlowable();

        Flowable<Long> activeTasksCount = tasksFlowable
                .filter(task -> !task.isCompleted())
                .count()
                .toFlowable();

        mCompositeDisposable.clear();
        Disposable disposable = Flowable.zip(completedTasksCount, activeTasksCount,
                Pair::create)
                .subscribeOn(mSchedulerProvider.compucation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(this::showStatistics,
                        throwable -> showError(),
                        () -> showIndicator(false));
        mCompositeDisposable.add(disposable);
    }

    private void showIndicator(boolean show) {
        if (mStatisticsView != null) {
            mStatisticsView.showLoadingIndicator(show);
        }
    }

    private void showError() {
        if (mStatisticsView != null) {
            mStatisticsView.showLoadingIndicator(false);
            mStatisticsView.showLoadingStatisticsError();
        }
    }

    private void showStatistics(Pair<Long, Long> pair) {
        if (mStatisticsView != null) {
            mStatisticsView.showTasksStatistics(String.valueOf(pair.second),
                    String.valueOf(pair.first));
        }
    }
}
