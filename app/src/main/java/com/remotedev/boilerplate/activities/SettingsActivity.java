package com.remotedev.boilerplate.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.remotedev.boilerplate.R;
import com.remotedev.boilerplate.fragments.SettingsFragment;
import com.remotedev.boilerplate.interfaces.NavigationInteraction;
import com.remotedev.boilerplate.navigation.FragNavController;

/**
 * SettingsActivity which contains SettingsFragment for giving the user options to change settings.
 */
public class SettingsActivity extends _BaseActivity implements NavigationInteraction {
    // tag for logging
    protected final String TAG = getClass().getSimpleName();

    private FragNavController mNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        Fragment rootFragment = SettingsFragment.newInstance();

        // Setup navigation
        mNavController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.content) // Initialize FragmentManager and fragment container
                .rootFragment(rootFragment)
                .build(); // return FragNavController instance
    }

    @Override
    public void pushFragment(Fragment fragment) {
        if (!isFinishing() && mNavController != null) {
            mNavController.pushFragment(fragment);
        }
    }

    @Override
    public void popFragment() {
        if (!isFinishing() && mNavController != null && !mNavController.isRootFragment()) {
            mNavController.popFragment();
        }
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        if (mNavController != null) {
            mNavController.replaceFragment(fragment);
        }
    }

    @Override
    public Fragment getCurrentFragment() {
        return mNavController.getCurrentFrag();
    }

    @Override
    public void finishActivity() {
        finish();
    }

    /**
     * Method onBackPressed, handling the 'physical' back button
     */
    @Override
    public void onBackPressed() {
        if (!mNavController.isRootFragment()) {
            mNavController.popFragment();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Method onOptionsItemSelected, handling the 'toolbar' back button
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
