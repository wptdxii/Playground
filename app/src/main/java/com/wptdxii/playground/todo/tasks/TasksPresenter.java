package com.wptdxii.playground.todo.tasks;

import android.support.annotation.NonNull;

import com.wptdxii.playground.todo.data.source.Task;
import com.wptdxii.playground.todo.tasks.usecase.CheckTasks;
import com.wptdxii.playground.todo.tasks.usecase.ClearAllTasks;
import com.wptdxii.playground.todo.tasks.usecase.ClearCompletedTasks;
import com.wptdxii.playground.todo.tasks.usecase.GetTaskss;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.subscribers.DisposableSubscriber;

final class TasksPresenter implements TasksContract.Presenter {

    private TasksContract.View mTaskView;
    private TasksFilterType mFilterType = TasksFilterType.ALL_TASKS;
    //    private final TasksRepository mTasksRepository;
    //    private final ISchedulerProvider mSchedulerProvider;
    //    private final CompositeDisposable mCompositeDisposable;
    private boolean mFirstLoad = true;

    //    private final GetTasks mGetTasks;
    private final ClearAllTasks mClearAllTasks;
    //    private final CheckTask mCheckTask;
    private final CheckTasks mCheckTasks;
    private final ClearCompletedTasks mClearCompletedTasks;

    private final GetTaskss mGetTaskss;

    @Inject
    TasksPresenter(@NonNull GetTaskss getTaskss,
                   //                   @NonNull CheckTask checkTask,
                   @NonNull CheckTasks checkTasks,
                   @NonNull ClearAllTasks clearAllTasks,
                   @NonNull ClearCompletedTasks clearCompletedTasks) {
        mGetTaskss = getTaskss;
        mCheckTasks = checkTasks;
        mClearAllTasks = clearAllTasks;
        mClearCompletedTasks = clearCompletedTasks;
    }

    //    @Inject
    //    TasksPresenter(@NonNull TasksRepository tasksRepository,
    //                   @NonNull ISchedulerProvider schedulerProvider,
    //                   @NonNull CompositeDisposable compositeDisposable) {
    //        mTasksRepository = tasksRepository;
    //        mSchedulerProvider = schedulerProvider;
    //        mCompositeDisposable = compositeDisposable;
    //    }

    @Override
    public void checkTask(@NonNull Task task) {
        //        mTasksRepository.updateTask(task);
        //        Flowable<Void> voidFlowable = mCheckTask.buildUseCase(new CheckTask.Request(task));
        //        voidFlowable.subscribe(aVoid -> mTaskView.showTaskChecked(task.isCompleted()));
        //        CheckTask.Request request = new CheckTask.Request(task);
        //        mCheckTask.subscribe(request, new DisposableSubscriber<Void>() {
        //            @Override
        //            public void onNext(Void aVoid) {
        //
        //            }
        //
        //            @Override
        //            public void onError(Throwable t) {
        //
        //            }
        //
        //            @Override
        //            public void onComplete() {
        //                mTaskView.showTaskChecked(task.isCompleted());
        //            }
        //        });

        CheckTasks.Request request = new CheckTasks.Request(task);
        mCheckTasks.subscribe(request);

        loadTasks(false, false);
        //        if (mTaskView != null) {
        //            mTaskView.showTaskChecked(task.isCompleted());
        //        }
    }

    @Override
    public void clearCompletedTasks() {
        //        mTasksRepository.deleteCompletedTasks();
        //        loadTasks(false, false);
        //        if (mTaskView != null) {
        //            mTaskView.showCompletedTasksCleared();
        //        }

        mClearCompletedTasks.subscribe(new ClearCompletedTasks.Request(), new DisposableSubscriber<Void>() {
            @Override
            public void onNext(Void aVoid) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                loadTasks(false, false);
                mTaskView.showCompletedTasksCleared();
            }
        });
    }

    @Override
    public void clearAllTasks() {
        //        mTasksRepository.deleteAllTasks();
        //        if (mTaskView != null) {
        //        }

        mClearAllTasks.subscribe(new ClearAllTasks.Reqeust(), new DisposableSubscriber<Void>() {
            @Override
            public void onNext(Void aVoid) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                mTaskView.showAllTasksCleared();
                mTaskView.showNoTasks(mFilterType);
            }
        });
    }


    @Override
    public void loadTasks(boolean forceUpdate, boolean showLoadingIndicator) {
        //        if (forceUpdate || mFirstLoad) mTasksRepository.refreshTasks();

        //        if (mTaskView != null && (showLoadingIndicator || mFirstLoad)) {
        //            mTaskView.showLoadingIndicator(true);
        //        }
        GetTaskss.Reqeust reqeust = new GetTaskss.Request(forceUpdate, showLoadingIndicator, mFilterType);
        mGetTaskss.subscribe(reqeust, new DisposableSubscriber<List<Task>>() {
            @Override
            public void onNext(List<Task> tasks) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });

        //        GetTasks.Request request = new GetTasks.Request(forceUpdate, showLoadingIndicator, mFilterType);
        //        mGetTasks.subscribe(request, new DisposableSubscriber<List<Task>>() {
        //            @Override
        //            protected void onStart() {
        //                super.onStart();
        //                mTaskView.showLoadingIndicator(true);
        //            }
        //
        //            @Override
        //            public void onNext(List<Task> tasks) {
        //                mTaskView.showLoadingIndicator(false);
        //                processTasks(tasks);
        //                mFirstLoad = false;
        //            }
        //
        //            @Override
        //            public void onError(Throwable t) {
        //                showError(showLoadingIndicator);
        //            }
        //
        //            @Override
        //            public void onComplete() {
        //                showSuccess(showLoadingIndicator);
        //            }
        //        });

        //        mCompositeDisposable.clear();
        //        Disposable disposable = mTasksRepository
        //                .getTasks()
        //                .flatMap(Flowable::fromIterable)
        //                .filter(task -> {
        //                    switch (mFilterType) {
        //                        case ACTIVE_TASKS:
        //                            return !task.isCompleted();
        //                        case COMPLETED_TASKS:
        //                            return task.isCompleted();
        //                        case ALL_TASKS:
        //                        default:
        //                            return true;
        //                    }
        //                }).toList()
        //                .subscribeOn(mSchedulerProvider.io())
        //                .observeOn(mSchedulerProvider.ui())
        //                .subscribe(tasks -> {
        //                }, throwable -> {
        //                });
        //
        //        mCompositeDisposable.add(disposable);
    }

    private void showSuccess(boolean showLoadingIndicator) {
        if ((showLoadingIndicator || mFirstLoad)) {
            mTaskView.showLoadingIndicator(false);
            mFirstLoad = false;
        }
    }

    private void showError(boolean showLoadingIndicator) {
        if (showLoadingIndicator || mFirstLoad) {
            mTaskView.showLoadingError();
            mTaskView.showLoadingIndicator(false);
            mFirstLoad = false;
        }
        mTaskView.showNoTasks(mFilterType);
    }

    private void processTasks(List<Task> tasks) {
        if (mTaskView != null) {
            if (tasks.isEmpty()) {
                mTaskView.showNoTasks(mFilterType);
            } else {
                mTaskView.showTasks(tasks);
                mTaskView.showFilterLabel(mFilterType);
            }
        }
    }

    @Override
    public TasksFilterType getFilter() {
        return mFilterType;
    }

    @Override
    public void setFilter(TasksFilterType filterType) {
        this.mFilterType = filterType;
    }

    @Override
    public void attach(TasksContract.View view) {
        this.mTaskView = view;
        loadTasks(false, false);
    }

    @Override
    public void detach() {
        mGetTasks.unsubscribe();
        //        mCheckTask.unsubscribe();
        mCheckTasks.unsubscribe();
        mClearAllTasks.unsubscribe();
        mTaskView = null;
        //        mCompositeDisposable.clear();
    }
}
