package com.wptdxii.framekit.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Iterator;
import java.util.Objects;
import java.util.Stack;

public class ActivityStack {

    private Stack<Activity> mActivityStack = new Stack<>();

    private ActivityStack() {
        if (SingletonHolder.INSTANCE != null) {
            throw new UnsupportedOperationException("already been instantiated");
        }
    }

    private static class SingletonHolder {
        private static final ActivityStack INSTANCE = new ActivityStack();
    }


    public static ActivityStack getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public int getCount() {
        return mActivityStack.size();
    }

    public void addActivity(Activity activity) {
        mActivityStack.add(activity);
    }

    public Activity getCurrentActivity() {
        if (mActivityStack.isEmpty()) {
            return null;
        } else {
            return mActivityStack.lastElement();
        }
    }

    public Activity getActivity(Class<?> cls) {
        Activity targetActivity = null;
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(cls)) {
                targetActivity = activity;
                break;
            }
        }

        return targetActivity;
    }

    public void removeActivity(Activity activity) {
        if (mActivityStack.contains(activity)) {
            mActivityStack.remove(activity);
        }
    }

    public void finishLastActivity() {
        Activity activity = mActivityStack.lastElement();
        finishActivity(activity);
    }

    public void finishActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity.finish();
        }
    }

    public void finishActivity(Class<?> cls) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    public void finishAll() {
        for (int i = 0; i < mActivityStack.size(); i++) {
            if (mActivityStack.get(i) != null) {
                Activity activity = mActivityStack.get(i);
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
        mActivityStack.clear();
    }

    public boolean isContainActivity(Class<?> cls) {
        boolean isContain = false;
        Iterator<Activity> iterator = mActivityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity.getClass().equals(cls)) {
                isContain = true;
                break;
            }
        }
        return isContain;
    }

    public void appExit(Context context) {
        try {
            finishAll();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            Objects.requireNonNull(activityMgr).killBackgroundProcesses(context.getPackageName());
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            Runtime.getRuntime().exit(-1);
        }
    }
}
