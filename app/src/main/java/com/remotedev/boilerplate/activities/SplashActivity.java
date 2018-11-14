package com.remotedev.boilerplate.activities;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import com.remotedev.boilerplate.R;
import com.remotedev.boilerplate.enums.PreferenceEnum;
import com.remotedev.boilerplate.utils.DebugUtils;
import com.remotedev.boilerplate.variables.GlobalVariables;
import com.remotedev.boilerplate.variables.PreferenceVariables;

/**
 * Splash Activity
 *
 * Activity for showing the splash screen
 */
public class SplashActivity extends _BaseActivity {
    private String TAG = getClass().getSimpleName();

    private Handler handler;
    private RelativeLayout txtContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (GlobalVariables.CURRENT_BUILD.erasePreferences) {
            DebugUtils.removePreferencesFromDevice();
        }

        setContentView(R.layout.activity_splash);
        txtContainer = findViewById(R.id.txtContainer);
    }

    /**
     * When activity stops delete the callback
     */
    @Override
    protected void onStop() {

        if (handler != null) {
            handler.removeCallbacks(runnable);
        }

        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // start handler
        handler = new Handler();
        handler.postDelayed(runnable, 1250);
    }


    /**
     * Runnable for going to next page
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            nextPage();
        }
    };

    /**
     * Go to the next page
     */
    private void nextPage() {
        Log.d(TAG, "SplashScreen is done, go to the main activity.");

        // start animation on txtContainer
        YoYo.with(Techniques.FadeIn).duration(1500).onStart(new YoYo.AnimatorCallback() {
            @Override
            public void call(Animator animator) {
                txtContainer.setVisibility(View.VISIBLE);
            }
        }).onEnd(new YoYo.AnimatorCallback() {
            @Override
            public void call(Animator animator) {
                // start the main activity
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mainIntent);

                // if the onboarding has to be showed,
                // start the onboardingActivity on top of the mainactivity
                if(PreferenceVariables.getIntPreference(getApplicationContext(), PreferenceEnum.ONBOARDING_VERSION_SEEN)
                        < OnboardingActivity.ONBOARDING_VERSION ) {
                    Intent onboardingIntent = new Intent(SplashActivity.this, OnboardingActivity.class);
                    startActivity(onboardingIntent);
                }

                finish();
            }
        }).playOn(txtContainer);
    }
}