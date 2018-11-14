package com.remotedev.boilerplate.fragments;

import com.remotedev.boilerplate.R;
import com.remotedev.boilerplate.contracts.ProfileContract;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.remotedev.boilerplate.presenters.ProfilePresenter;
import com.remotedev.boilerplate.utils.CustomDialog;

/**
 * Profile Fragment, meant fas an example fragment.
 */
public class ProfileFragment extends _BaseFragment implements View.OnClickListener, ProfileContract.View {
    private final String TAG = getClass().getSimpleName();

    private ProfileContract.Presenter mPresenter;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupToolBar(view, true, getResources().getString(R.string.profile_title));

        mPresenter = new ProfilePresenter(this, getContext());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dialog:
                mPresenter.showDialog();
        }
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void displayCustomDialog(String messageTitle, String message, String actionTitle) {
        if (isAdded()) {
            CustomDialog.showCustomDialog(getContext(), actionTitle, messageTitle, message);
        }
    }

}
