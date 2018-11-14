package com.remotedev.boilerplate.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.remotedev.boilerplate.R;
import com.remotedev.boilerplate.enums.PreferenceEnum;
import com.remotedev.boilerplate.variables.PreferenceVariables;

/**
 * Created by Erwin on 26-03-18.
 */

public class SettingsFragment extends _BaseFragment implements View.OnClickListener {
    // tag for logging
    protected final String TAG = getClass().getSimpleName();

    /**
     * Empty Constructor
     */
    public SettingsFragment() {
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupToolBar(view, true, getString(R.string.settings_title));

        // This takes the user to the profile page
        FrameLayout linkToProfilePage = view.findViewById(R.id.user_profile_button);
        linkToProfilePage.setOnClickListener(this);

        // This takes the user to the about page
        FrameLayout linkToAboutAppPage = view.findViewById(R.id.about_app_button);
        linkToAboutAppPage.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_profile_button:
                mFragmentNavigation.pushFragment(ProfileFragment.newInstance());
                break;
            case R.id.about_app_button:
                mFragmentNavigation.pushFragment(AboutThisAppFragment.newInstance());
                break;
        }
    }
}
