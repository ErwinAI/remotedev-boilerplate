package com.remotedev.boilerplate.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static com.remotedev.boilerplate._BaseApplication.getAppContext;

/**
 * DebugUtils contains methods for debugging purposes ONLY
 *
 * Created by Erwin on 18-2-2018.
 */

public class DebugUtils {

    /**
     * Removes all shared preferences from the device
     */
    public static void removePreferencesFromDevice() {
        if (getAppContext() != null) {
            SharedPreferences sp = getAppContext().getSharedPreferences(getAppContext().getPackageName(), Context.MODE_PRIVATE);
            sp.edit().clear().apply();
        }
    }
}
