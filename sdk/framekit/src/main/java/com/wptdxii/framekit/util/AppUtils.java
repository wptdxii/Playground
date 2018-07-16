package com.wptdxii.framekit.util;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.List;
import java.util.Objects;

/**
 * Created by wptdxii on 2016/9/13 0013.
 */
public class AppUtils {

    private static Bundle sOwnAppMetaInfo;

    private AppUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isForeground(Context context) {
        if (isScreenLocked(context)) {
            return false;
        }
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = Objects.requireNonNull(mActivityManager).getRunningTasks(1);
        return context.getPackageName().equals(runningTaskInfos.get(0).baseActivity.getPackageName())
                && context.getPackageName().equals(runningTaskInfos.get(0).topActivity.getPackageName());
    }

    private static boolean isScreenLocked(Context context) {
        KeyguardManager mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        return Objects.requireNonNull(mKeyguardManager).inKeyguardRestrictedInputMode();
    }

    public static int getVersionCode(Context context) {
        if (context == null) {
            return 0;
        }
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Throwable e) {
            return 0;
        }
    }

    /**
     * 获取带有指定配置信息的ApplicationInfo
     *
     * @param flags 比如 {@link PackageManager#GET_META_DATA}
     * @see #getSimpleAppInfo(Context)
     * @see <a href="https://code.google.com/p/android/issues/detail?id=37968" >why this method</a>
     */
    public static ApplicationInfo getAppInfoWithFlags(Context ctx, int flags) {
        try {
            return ctx == null ? null : ctx.getPackageManager().getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ApplicationInfo getSimpleAppInfo(Context ctx) {
        return ctx == null ? null : ctx.getApplicationInfo();
    }

    public static boolean isPackageInstalled(Context context, String packageName) {
        if (!TextUtils.isEmpty(packageName)) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
                return packageInfo != null;
            } catch (NameNotFoundException e) {
                System.out.print(e);
            }
        }
        return false;
    }

    public static Bundle getApplicationMetaInfo(Context context) {
        if (sOwnAppMetaInfo == null) {
            synchronized (AppUtils.class) {
                if (sOwnAppMetaInfo == null) {
                    sOwnAppMetaInfo = getApplicationMetaInfo(context, context.getPackageName());
                }
            }
        }
        return sOwnAppMetaInfo != null ? new Bundle(sOwnAppMetaInfo) : null;
    }

    public static Bundle getApplicationMetaInfo(Context context, String packageName) {
        ApplicationInfo appInfo = packageName.equals(context.getPackageName()) ?
                context.getApplicationInfo() : null;
        Bundle metaInfo = appInfo != null ? appInfo.metaData : null;
        if (metaInfo == null) {
            try {
                appInfo = context.getPackageManager().getApplicationInfo(
                        packageName, PackageManager.GET_META_DATA);
                metaInfo = appInfo.metaData;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return metaInfo;
    }
}
