package com.wptdxii.playground.sample;

import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;

import com.wptdxii.playground.R;
import com.wptdxii.playground.base.BaseListActivity;
import com.wptdxii.playground.base.Module;

import java.util.ArrayList;
import java.util.List;

public class SampleActivity extends BaseListActivity {

    @Override
    protected void setupToolbar(Toolbar toolbar) {
        toolbar.setTitle(R.string.module_sample);

    }

    @NonNull
    @Override
    protected List<Module> createModules() {
        List<Module> modules = new ArrayList<>();
        modules.add(new Module(getString(R.string.sample_layout_title), LayoutActivity.class));
        modules.add(new Module(getString(R.string.sample_drawer_title), DrawerActivity.class));
        return modules;
    }
}
