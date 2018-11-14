package com.remotedev.boilerplate.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import com.remotedev.boilerplate.variables.GlobalVariables;

/**
 * Base activity containing standard onCreate
 *
 * Created by Erwin on 18/02/2018.
 */
public abstract class _BaseActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // log the build type
        Log.d("Build", "Started with build type '" + GlobalVariables.CURRENT_BUILD.buildType + "'");
    }
}
