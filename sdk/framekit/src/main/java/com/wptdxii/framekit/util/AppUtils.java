package com.wptdxii.framekit.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import com.wptdxii.framekit.Extension;
import com.wptdxii.framekit.exception.InstantiationException;

import java.io.File;
import java.util.List;

public final class AppUtils {

    private AppUtils() {
        throw new InstantiationException();
    }

    /**
     * Return whether the app is installed.
     *
     * @param action   The Intent action, such as ACTION_VIEW.
     * @param category The desired category.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppInstalled(@NonNull final String action,
                                         @NonNull final String category) {
        Intent intent = new Intent(action);
        intent.addCategory(category);
        PackageManager pm = Extension.getExtension().getApplication().getPackageManager();
        ResolveInfo info = pm.resolveActivity(intent, 0);
        return info != null;
    }

    /**
     * Install the app.
     * <p>Target APIs greater than 25 must hold
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param filePath The path of file.
     */
    public static void installApp(final String filePath) {
        installApp(getFileByPath(filePath));
    }

    /**
     * Install the app.
     * <p>Target APIs greater than 25 must hold
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param file The file.
     */
    public static void installApp(final File file) {
        if (!isFileExists(file)) return;
        Intent intent = getInstallAppIntent(file);
        Extension.getExtension().getApplication().startActivity(intent);
    }

    private static Intent getInstallAppIntent(File file) {
        if (file == null) return null;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        String type = "application/vnd.android.package-archive";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            data = Uri.fromFile(file);
        } else {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            String authority = Extension.getExtension().getApplication().getPackageName() + ".provider";
            data = FileProvider.getUriForFile(Extension.getExtension().getApplication(), authority, file);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(data, type);
        return intent;
    }

    /**
     * Install the app.
     * <p>Target APIs greater than 25 must hold
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param activity    The activity.
     * @param filePath    The path of file.
     * @param requestCode If &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     */
    public static void installApp(final Activity activity,
                                  final String filePath,
                                  final int requestCode) {
        installApp(activity, getFileByPath(filePath), requestCode);
    }

    /**
     * Install the app.
     * <p>Target APIs greater than 25 must hold
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param activity    The activity.
     * @param file        The file.
     * @param requestCode If &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     */
    public static void installApp(final Activity activity,
                                  final File file,
                                  final int requestCode) {
        if (!isFileExists(file)) return;
        activity.startActivityForResult(getInstallAppIntent(file), requestCode);
    }

    /**
     * Install the app silently.
     * <p>Without root permission must hold
     * {@code <uses-permission android:name="android.permission.INSTALL_PACKAGES" />}</p>
     *
     * @param filePath The path of file.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean installAppSilent(final String filePath) {
        return installAppSilent(getFileByPath(filePath), null);
    }

    /**
     * Install the app silently.
     * <p>Without root permission must hold
     * {@code <uses-permission android:name="android.permission.INSTALL_PACKAGES" />}</p>
     *
     * @param file The file.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean installAppSilent(final File file) {
        return installAppSilent(file, null);
    }


    /**
     * Install the app silently.
     * <p>Without root permission must hold
     * {@code <uses-permission android:name="android.permission.INSTALL_PACKAGES" />}</p>
     *
     * @param filePath The path of file.
     * @param params   The params of installation(e.g.,<code>-r</code>, <code>-s</code>).
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean installAppSilent(final String filePath, final String params) {
        return installAppSilent(getFileByPath(filePath), params);
    }

    /**
     * Install the app silently.
     * <p>Without root permission must hold
     * {@code <uses-permission android:name="android.permission.INSTALL_PACKAGES" />}</p>
     *
     * @param file   The file.
     * @param params The params of installation(e.g.,<code>-r</code>, <code>-s</code>).
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean installAppSilent(final File file, final String params) {
        return installAppSilent(file, params, isDeviceRooted());
    }

    /**
     * Install the app silently.
     * <p>Without root permission must hold
     * {@code <uses-permission android:name="android.permission.INSTALL_PACKAGES" />}</p>
     *
     * @param file     The file.
     * @param params   The params of installation(e.g.,<code>-r</code>, <code>-s</code>).
     * @param isRooted True to use root, false otherwise.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean installAppSilent(final File file,
                                           final String params,
                                           final boolean isRooted) {
        if (!isFileExists(file)) return false;
        String filePath = '"' + file.getAbsolutePath() + '"';
        String command = "LD_LIBRARY_PATH=/vendor/lib*:/system/lib* pm install " +
                (params == null ? "" : params + " ")
                + filePath;
        ShellUtils.CommandResult commandResult = ShellUtils.execCmd(command, isRooted);
        return commandResult.successMsg != null
                && commandResult.successMsg.toLowerCase().contains("success");
    }

    /**
     * Uninstall the app.
     *
     * @param packageName The name of the package.
     */
    public static void uninstallApp(final String packageName) {
        if (isSpace(packageName)) return;
        Extension.getExtension().getApplication().startActivity(getUninstallAppIntent(packageName, true));
    }

    private static Intent getUninstallAppIntent(String packageName, boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        if (isNewTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.setData(Uri.parse("package:" + packageName));
        return intent;
    }

    /**
     * Uninstall the app.
     *
     * @param activity    The activity.
     * @param packageName The name of the package.
     * @param requestCode If &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     */
    public static void uninstallApp(final Activity activity,
                                    final String packageName,
                                    final int requestCode) {
        if (isSpace(packageName)) return;
        activity.startActivityForResult(getUninstallAppIntent(
                packageName, false), requestCode
        );
    }

    /**
     * Uninstall the app silently.
     * <p>Without root permission must hold
     * {@code <uses-permission android:name="android.permission.DELETE_PACKAGES" />}</p>
     *
     * @param packageName The name of the package.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean uninstallAppSilent(final String packageName) {
        return uninstallAppSilent(packageName, false);
    }

    /**
     * Uninstall the app silently.
     * <p>Without root permission must hold
     * {@code <uses-permission android:name="android.permission.DELETE_PACKAGES" />}</p>
     *
     * @param packageName The name of the package.
     * @param isKeepData  Is keep the data.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean uninstallAppSilent(final String packageName, final boolean isKeepData) {
        return uninstallAppSilent(packageName, isKeepData, isDeviceRooted());
    }

    /**
     * Uninstall the app silently.
     * <p>Without root permission must hold
     * {@code <uses-permission android:name="android.permission.DELETE_PACKAGES" />}</p>
     *
     * @param packageName The name of the package.
     * @param isKeepData  Is keep the data.
     * @param isRooted    True to use root, false otherwise.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean uninstallAppSilent(final String packageName,
                                             final boolean isKeepData,
                                             final boolean isRooted) {
        if (isSpace(packageName)) return false;
        String command = "LD_LIBRARY_PATH=/vendor/lib*:/system/lib* pm uninstall "
                + (isKeepData ? "-k " : "")
                + packageName;
        ShellUtils.CommandResult commandResult = ShellUtils.execCmd(command, isRooted);
        return commandResult.successMsg != null
                && commandResult.successMsg.toLowerCase().contains("success");
    }

    /**
     * Return whether the app is installed.
     *
     * @param packageName The name of the package.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppInstalled(@NonNull final String packageName) {
        Intent intent = Extension.getExtension().getApplication().getPackageManager()
                .getLaunchIntentForPackage(packageName);
        return !isSpace(packageName) && intent != null;
    }

    /**
     * Return whether it is a debug application.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppDebug() {
        return isAppDebug(Extension.getExtension().getApplication().getPackageName());
    }

    /**
     * Return whether it is a debug application.
     *
     * @param packageName The name of the package.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppDebug(final String packageName) {
        if (isSpace(packageName)) return false;
        try {
            PackageManager pm = Extension.getExtension().getApplication().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Return whether application is foreground.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppForeground() {
        ActivityManager am =
                (ActivityManager) Extension.getExtension().getApplication().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return false;
        List<ActivityManager.RunningAppProcessInfo> info = am.getRunningAppProcesses();
        if (info == null || info.size() == 0) return false;
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return aInfo.processName.equals(Extension.getExtension().getApplication().getPackageName());
            }
        }
        return false;
    }

    /**
     * Relaunch the application.
     */
    public static void relaunchApp() {
        Application application = Extension.getExtension().getApplication();
        PackageManager packageManager = application.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(application.getPackageName());
        if (intent == null) return;
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        application.startActivity(mainIntent);
        System.exit(0);
    }

    /**
     * Return the application's package name.
     *
     * @return the application's package name
     */
    public static String getAppPackageName() {
        return Extension.getExtension().getApplication().getPackageName();
    }

    /**
     * Return the application's name.
     *
     * @return the application's name
     */
    public static String getAppName() {
        return getAppName(Extension.getExtension().getApplication().getPackageName());
    }

    /**
     * Return the application's name.
     *
     * @param packageName The name of the package.
     * @return the application's name
     */
    public static String getAppName(final String packageName) {
        if (isSpace(packageName)) return "";
        try {
            PackageManager pm = Extension.getExtension().getApplication().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Return the application's version name.
     *
     * @return the application's version name
     */
    public static String getAppVersionName() {
        return getAppVersionName(Extension.getExtension().getApplication().getPackageName());
    }

    /**
     * Return the application's version name.
     *
     * @param packageName The name of the package.
     * @return the application's version name
     */
    public static String getAppVersionName(final String packageName) {
        if (isSpace(packageName)) return "";
        try {
            PackageManager pm = Extension.getExtension().getApplication().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Return the application's version code.
     *
     * @return the application's version code
     */
    public static int getAppVersionCode() {
        return getAppVersionCode(Extension.getExtension().getApplication().getPackageName());
    }

    /**
     * Return the application's version code.
     *
     * @param packageName The name of the package.
     * @return the application's version code
     */
    public static int getAppVersionCode(final String packageName) {
        if (isSpace(packageName)) return -1;
        try {
            PackageManager pm = Extension.getExtension().getApplication().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }


    private static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    private static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isDeviceRooted() {
        String su = "su";
        String[] locations = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/",
                "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/"};
        for (String location : locations) {
            if (new File(location + su).exists()) {
                return true;
            }
        }
        return false;
    }
}
