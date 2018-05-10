package com.wptdxii.playground.todo.todo_mvp.statistics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wptdxii.framekit.base.BaseFragment;
import com.wptdxii.playground.R;
import com.wptdxii.playground.todo.todo_mvp.data.source.Injection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StatisticsFragment extends BaseFragment implements StatisticsContract.View {

    @BindView(R.id.tv_tasks_statistics)
    TextView tvTasksStatistics;
    Unbinder unbinder;

    private StatisticsPresenter mStatisticsPresenter;

    public static Fragment newInstance() {
        return new StatisticsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStatisticsPresenter = new StatisticsPresenter(Injection.provideTasksRepository(getContext()));
        mStatisticsPresenter.attach(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.todo_fragment_statistics, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mStatisticsPresenter.getTasksStatistics();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mStatisticsPresenter.detach();
    }

    @Override
    public void showLoadingIndicator(boolean show) {
        if (show) {
            tvTasksStatistics.setText(R.string.todo_loading);
        } else {
            tvTasksStatistics.setText("");
        }
    }

    @Override
    public void showTasksStatistics(String active, String completed) {
        String statistics = String.format(getString(R.string.todo_statistics_active_tasks), active) +
                "\n" + String.format(getString(R.string.todo_statistics_completed_tasks), completed);
        tvTasksStatistics.setText(statistics);
    }

    @Override
    public void showLoadingStatisticsError() {
        Toast.makeText(getContext(), R.string.todo_statistics_error_loading, Toast.LENGTH_SHORT).show();
    }
}
