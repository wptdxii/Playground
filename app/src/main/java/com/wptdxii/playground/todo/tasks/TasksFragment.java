package com.wptdxii.playground.todo.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wptdxii.framekit.base.BaseFragment;
import com.wptdxii.playground.R;
import com.wptdxii.playground.todo.addnewtask.AddNewTaskActivity;
import com.wptdxii.playground.todo.data.Injection;
import com.wptdxii.playground.todo.data.model.Task;
import com.wptdxii.playground.todo.taskdetail.TaskDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wptdxii on 2018/1/25 0025 18:01
 */

public class TasksFragment extends BaseFragment implements TasksContract.View {

    private static final String CURRENT_FILTERING_KEY = "current_filtering_key";

    @BindView(R.id.tv_filtering_label)
    TextView tvFilteringLabel;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.group_list)
    Group groupList;
    @BindView(R.id.tv_no_tasks)
    TextView tvNoTasks;
    @BindView(R.id.iv_no_tasks)
    ImageView ivNoTasks;
    @BindView(R.id.tv_no_tasks_add)
    TextView tvNoTasksAdd;
    @BindView(R.id.group_no_tasks)
    Group groupNoTasks;
    @BindView(R.id.cl_content)
    ConstraintLayout clContent;
    @BindView(R.id.fab_add_task)
    FloatingActionButton fabAddTask;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;

    private TasksAdapter mTasksAdapter;
    private TasksPresenter mPresenter;

    public static Fragment newInstance() {
        return new TasksFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TasksAdapter.TaskItemListener itemListener = new TasksAdapter.TaskItemListener() {
            @Override
            public void onTaskClick(Task clickedTask) {
                mPresenter.openTaskDetail(clickedTask);
            }

            @Override
            public void onCompleteTaskClick(Task completedTask) {
                mPresenter.completeTask(completedTask);
            }

            @Override
            public void onActivateTaskClick(Task activatedTask) {
                mPresenter.activateTask(activatedTask);
            }
        };

        mTasksAdapter = new TasksAdapter(new ArrayList<>(0), itemListener);
        mPresenter = new TasksPresenter(
                Injection.provideTasksRepository(Objects.requireNonNull(getContext())), this);

        if (savedInstanceState != null) {
            TasksFilterType filtering =
                    (TasksFilterType) savedInstanceState.getSerializable(CURRENT_FILTERING_KEY);
            mPresenter.setFiltering(filtering);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        unbinder = ButterKnife.bind(this, view);

        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(mTasksAdapter);

        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.todo_color_primary),
                ContextCompat.getColor(getContext(), R.color.todo_color_accent),
                ContextCompat.getColor(getContext(), R.color.todo_color_primary_dark));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            mPresenter.loadTasks(true, true);
        });

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.todo_tasks_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                showFilteringPopupMenu();
                break;
            case R.id.action_clear:
                mPresenter.clearCompletedTasks();
                break;
            case R.id.action_refresh:
                mPresenter.loadTasks(true, true);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showFilteringPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(Objects.requireNonNull(getContext()),
                Objects.requireNonNull(getActivity()).findViewById(R.id.action_filter));
        popupMenu.inflate(R.menu.todo_tasks_fragment_filter);

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_active:
                    mPresenter.setFiltering(TasksFilterType.ACTIVE_TASKS);
                    break;
                case R.id.action_completed:
                    mPresenter.setFiltering(TasksFilterType.COMPLETED_TASKS);
                    break;
                default:
                    mPresenter.setFiltering(TasksFilterType.ALL_TASKS);
                    break;
            }
            mPresenter.loadTasks(false, true);
            return true;
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mPresenter.detachView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadTasks(false, true);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CURRENT_FILTERING_KEY, mPresenter.getFiltering());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onActivityResult(requestCode, resultCode);
    }

    @OnClick({R.id.tv_no_tasks_add, R.id.fab_add_task})
    public void addNewTask() {
        showAddNewTask();
    }

    @Override
    public void showAddNewTask() {
        AddNewTaskActivity.startFortResult(getActivity());
    }

    @Override
    public void showNewTaskSaved() {
        showSnackBar("TO-DO saved");
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (getView() == null) {
            return;
        }

        // Make sure setRefreshing() is called after the layout is done with everything else.
        swipeRefreshLayout.post(() -> {
            swipeRefreshLayout.setRefreshing(active);
        });
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showTasks(@NonNull List<Task> tasks) {
        mTasksAdapter.resetTasks(tasks);
    }

    @Override
    public void setGroupViewVisibility(boolean showGroupList) {
        groupList.setVisibility(showGroupList ? View.VISIBLE : View.GONE);
        groupNoTasks.setVisibility(showGroupList ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showNoTasks() {
        showNoTasksViews(R.string.todo_tasks_fragment_no_todos,
                R.drawable.todo_vector_ic_assignment_turned_in, true);
    }

    @Override
    public void showNoActiveTasks() {
        showNoTasksViews(R.string.todo_tasks_fragment_no_active_todos,
                R.drawable.todo_vector_ic_assignment_turned_in, false);
    }

    @Override
    public void showNoCompletedTasks() {
        showNoTasksViews(R.string.todo_tasks_fragment_no_completed_todos,
                R.drawable.todo_vector_ic_verfied_user, false);
    }

    private void showNoTasksViews(@StringRes int mainText, @DrawableRes int iconId,
                                  boolean showAddView) {
        tvNoTasks.setText(mainText);
        ivNoTasks.setImageResource(iconId);
        tvNoTasksAdd.setVisibility(showAddView ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showAllFilterLabel() {
        tvFilteringLabel.setText(R.string.todo_tasks_fragment_filter_label_all);
    }

    @Override
    public void showActiveFilterLabel() {
        tvFilteringLabel.setText(R.string.todo_tasks_fragment_filter_label_active);
    }

    @Override
    public void showCompletedFilterLabel() {
        tvFilteringLabel.setText(R.string.todo_tasks_fragment_filter_label_completed);
    }

    @Override
    public void showTaskDetail(@NonNull String taskId) {
        TaskDetailActivity.start(Objects.requireNonNull(getContext()), taskId);
    }

    @Override
    public void showTaskMarkedComplete() {
        showSnackBar(getString(R.string.todo_tasks_fragment_task_marked_complete));
    }

    @Override
    public void showTaskMarkedActivate() {
        showSnackBar(getString(R.string.todo_tasks_fragment_task_marked_active));
    }

    @Override
    public void showClearCompletedTasks() {
        showSnackBar(getString(R.string.todo_tasks_fragment_task_completed_cleared));
    }

    private void showSnackBar(String message) {
        Snackbar.make(fabAddTask, message, Snackbar.LENGTH_LONG).show();
    }
}
