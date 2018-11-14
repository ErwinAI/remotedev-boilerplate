package com.remotedev.boilerplate.variables;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.remotedev.boilerplate.enums.PreferenceEnum;

/**
 * PreferenceVariables containing methods to obtain and set different types of preferences
 *
 * Created by Erwin on 26-03-2018.
 */
public class PreferenceVariables {
    private static final String TAG = PreferenceVariables.class.getSimpleName();
    private static SharedPreferences preferences;

    /**
     * Obtain preference value of type String
     * @param context context
     * @param preference preference enum containing the key
     * @return value corresponding with the preference key
     */
    public static String getStringPreference(Context context, PreferenceEnum preference) {
        if(obtainPreferences(context)) {
            return preferences.getString(preference.key, "");
        } else {
            return "";
        }
    }

    /**
     * Obtain preference value of type Integer
     * @param context context
     * @param preference preference enum containing the key
     * @return value corresponding with the preference key
     */
    public static int getIntPreference(Context context, PreferenceEnum preference) {
        if(obtainPreferences(context)) {
            return preferences.getInt(preference.key, 0);
        } else {
            return 0;
        }
    }

    /**
     * Configure a preference with type String
     * @param preference preference enum containing the key
     * @param value value to be saved to the preference key
     * @param context context
     */
    public static void setStringPreference(PreferenceEnum preference, String value, Context context) {
        if(obtainPreferences(context)) {
            preferences.edit().putString(preference.key, value).apply();
        }
    }

    /**
     * Configure a preference with type Integer
     * @param preference preference enum containing the key
     * @param value value to be saved to the preference key
     * @param context context
     */
    public static void setIntPreference(PreferenceEnum preference, int value, Context context) {
        if(obtainPreferences(context)) {
            preferences.edit().putInt(preference.key, value).apply();
        }
    }




    /**
     * Obtain preferences
     * @param context context
     * @return boolean whether or not it was successful
     */
    private static boolean obtainPreferences(Context context) {
        if(preferences != null) {
            return true;
        } else {
            if (context == null) {
                Log.e(TAG, "obtainPreferences called on null context.");
                return false;
            }

            preferences = context.getSharedPreferences(context.getApplicationContext().getPackageName(), Context.MODE_PRIVATE);

            if (preferences == null) {
                Log.e(TAG, "obtainPreferences preferences is null.");
                return false;
            }

            return true;
        }
    }


}







