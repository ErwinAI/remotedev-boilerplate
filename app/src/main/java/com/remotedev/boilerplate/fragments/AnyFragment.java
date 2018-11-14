package com.remotedev.boilerplate.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.remotedev.boilerplate.R;

/**
 * AnyFragment
 * <p>
 * Place Holder Fragment to facilitate redirection within the stories
 */
public class AnyFragment extends _BaseFragment {
    private String TAG = getClass().getSimpleName();

    /**
     * Make new instance
     *
     * @return AnyFragment
     */
    public static AnyFragment newInstance() {
        return new AnyFragment();
    }

    /**
     * Empty Constructor
     */
    public AnyFragment() {
    }

    /**
     * OnCreateView to inflate layout
     *
     * @param inflater           inflater
     * @param container          container
     * @param savedInstanceState savedInstanceState
     * @return View
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_any, container, false);
    }

    /**
     * OnViewCreated, setting initial values
     *
     * @param view               view
     * @param savedInstanceState savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupToolBar(view, false, getString(R.string.splash_title));
    }
}
