package com.remotedev.boilerplate.fragments;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import com.remotedev.boilerplate.R;

/**
 * AboutThisApp Fragment shows basic information about this app
 */
public class AboutThisAppFragment extends _BaseFragment {

    public AboutThisAppFragment() {
        // Required empty public constructor
    }

    public static AboutThisAppFragment newInstance() {
        return new AboutThisAppFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_this_app, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupToolBar(view, true, getString(R.string.settings_about_this_app));

        // Display Application Version
        TextView applicationVersionTextView = view.findViewById(R.id.app_version);
        applicationVersionTextView.setText(getApplicationVersion());
    }

    /**
     * This method retrieves the application version
     */
    private String getApplicationVersion(){
        try {
            PackageInfo pInfo = getContext().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Crashlytics.logException(e);
            return "";
        }
    }
}
