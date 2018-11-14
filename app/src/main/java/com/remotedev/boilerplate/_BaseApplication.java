package com.remotedev.boilerplate;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;

import io.fabric.sdk.android.Fabric;

/**
 * Base Application overwrite
 *
 * Created by Erwin on 18/02/2018.
 */

public class _BaseApplication extends MultiDexApplication {

    private static Context context;

    /**
     * OnCreate for BaseApplication
     * Obtains context of whole application for further use
     */
    public void onCreate() {
        super.onCreate();

        //TODO: enable fabric if API key is known
        // register and create fabric/crashlytics
        //Fabric.with(this, new Crashlytics.Builder()
        //                .core(new CrashlyticsCore.Builder().build())
         //               .build(), new Crashlytics());
        
        _BaseApplication.context = getApplicationContext();
    }

    /**
     * Getter to obtain application context anywhere
     * @return main application context
     */
    public static Context getAppContext() {
        return _BaseApplication.context;
    }
}