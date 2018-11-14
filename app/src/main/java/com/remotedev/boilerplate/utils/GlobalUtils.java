package com.remotedev.boilerplate.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.customtabs.CustomTabsIntent;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.crashlytics.android.Crashlytics;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.remotedev.boilerplate.R;

/**
 * GlobalUtils
 * <p>
 * Created by Erwin on 19-2-2018.
 */

public class GlobalUtils {

    private static final String TAG = GlobalUtils.class.getSimpleName();

    public static int dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float pxFloat = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
        return (int) pxFloat;
    }

    public static Drawable getAPICompatVectorDrawable(Context callingContext, int resource_id) {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            return ContextCompat.getDrawable(callingContext.getApplicationContext(), resource_id);
        } else {
            return VectorDrawableCompat.create(
                    callingContext.getResources(),
                    resource_id,
                    callingContext.getTheme());
        }
    }

    public static void hideKeyboard(Activity activity, View viewToRemoveFrom) {
        try {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            InputMethodManager manager = ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE));

            if ((activity.getCurrentFocus() != null) && (activity.getCurrentFocus().getWindowToken() != null)) {
                manager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
            if (viewToRemoveFrom != null && viewToRemoveFrom.getWindowToken() != null) {
                manager.hideSoftInputFromWindow(viewToRemoveFrom.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            Crashlytics.log(TAG + "Error while hiding Keyboard");
            Crashlytics.logException(e);
        }
    }

    /**
     * Method to open a custom tabs (webview like) page in the com.remotedev.boilerplate
     *
     * @param activity activity
     * @param url      url for terms
     */
    public static void openCustomTabsPage(Activity activity, String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.colorPrimary));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(activity, Uri.parse(url));
    }

    /**
     * Method to format current time
     *
     * @param time time
     * @return string of date
     */
    public static String formatDate(long time, Context context) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy", GlobalUtils.getLocale(context));
        Date resultdate = new Date(time);
        return sdf.format(resultdate);
    }

    /**
     * Method to format current time
     *
     * @param time time
     * @return string of date
     */
    public static String formatTime(long time, Context context) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", GlobalUtils.getLocale(context));
        Date resultdate = new Date(time);
        return sdf.format(resultdate);
    }

    public static Locale getLocale(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().getLocales().get(0);
        } else {
            return context.getResources().getConfiguration().locale;
        }
    }

    public static SimpleDateFormat getStandardSimpleDateFormat(Context context) {
        return new SimpleDateFormat("dd MMM, yyyy", GlobalUtils.getLocale(context));
    }

    public static Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}
