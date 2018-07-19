package com.wptdxii.framekit.util;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.view.View;

import com.wptdxii.framekit.Extension;
import com.wptdxii.framekit.exception.InstantiationException;

public final class ActivityUtils {

    private ActivityUtils() {
        throw new InstantiationException();
    }

    /**
     * Return whether the activity exists.
     *
     * @param pkg The name of the package.
     * @param cls The name of the class.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isActivityExists(@NonNull final String pkg,
                                           @NonNull final String cls) {
        Intent intent = new Intent();
        intent.setClassName(pkg, cls);
        Application application = Extension.getExtension().getApplication();
        return !(application.getPackageManager().resolveActivity(intent, 0) == null ||
                intent.resolveActivity(application.getPackageManager()) == null ||
                application.getPackageManager().queryIntentActivities(intent, 0).size() == 0);
    }

    /**
     * Start the activity.
     *
     * @param activity The activity.
     * @param clz      The activity class.
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Class<? extends Activity> clz) {
        startActivity(activity, null, activity.getPackageName(), clz.getName(), null);
    }

    /**
     * Start the activity.
     *
     * @param extras   The Bundle of extras to add to this intent.
     * @param activity The activity.
     * @param clz      The activity class.
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final Class<? extends Activity> clz) {
        startActivity(activity, extras, activity.getPackageName(), clz.getName(), null);
    }

    /**
     * Start the activity.
     *
     * @param activity The activity.
     * @param intent   The description of the activity to start.
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Intent intent) {
        startActivity(intent, activity, null);
    }

    /**
     * Start the activity.
     *
     * @param activity    The activity.
     * @param clz         The activity class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode) {
        startActivityForResult(activity, null, activity.getPackageName(), clz.getName(),
                requestCode, null);
    }

    /**
     * Start the activity.
     *
     * @param extras      The Bundle of extras to add to this intent.
     * @param activity    The activity.
     * @param clz         The activity class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     */
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Activity activity,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode) {
        startActivityForResult(activity, extras, activity.getPackageName(), clz.getName(),
                requestCode, null);
    }

    /**
     * Start the activity for result.
     *
     * @param activity    The activity.
     * @param intent      The description of the activity to start.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Intent intent,
                                              final int requestCode) {
        startActivityForResult(intent, activity, requestCode, null);
    }

    /**
     * Finish the activity.
     *
     * @param activity The activity.
     */
    public static void finishActivity(@NonNull final Activity activity) {
        finishActivity(activity, false);
    }

    /**
     * Finish the activity.
     *
     * @param activity   The activity.
     * @param isLoadAnim True to use animation for the outgoing activity, false otherwise.
     */
    public static void finishActivity(@NonNull final Activity activity, final boolean isLoadAnim) {
        activity.finish();
        if (!isLoadAnim) {
            activity.overridePendingTransition(0, 0);
        }
    }

    private static void startActivity(final Context context,
                                      final Bundle extras,
                                      final String pkg,
                                      final String cls,
                                      final Bundle options) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (extras != null) intent.putExtras(extras);
        intent.setComponent(new ComponentName(pkg, cls));
        startActivity(intent, context, options);
    }

    private static void startActivity(final Intent intent,
                                      final Context context,
                                      final Bundle options) {
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivity(intent, options);
        } else {
            context.startActivity(intent);
        }
    }

    private static void startActivityForResult(final Activity activity,
                                               final Bundle extras,
                                               final String pkg,
                                               final String cls,
                                               final int requestCode,
                                               final Bundle options) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (extras != null) intent.putExtras(extras);
        intent.setComponent(new ComponentName(pkg, cls));
        startActivityForResult(intent, activity, requestCode, options);
    }

    private static void startActivityForResult(final Intent intent,
                                               final Activity activity,
                                               final int requestCode,
                                               final Bundle options) {
        if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            activity.startActivityForResult(intent, requestCode, options);
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
    }
}
