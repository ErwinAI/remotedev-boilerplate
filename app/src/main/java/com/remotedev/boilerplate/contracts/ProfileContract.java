package com.remotedev.boilerplate.contracts;

import com.remotedev.boilerplate.BasePresenter;
import com.remotedev.boilerplate.BaseView;

/**
 * Created by Erwin on 07/05/2018.
 */
public interface ProfileContract {
    interface View extends BaseView<ProfileContract.Presenter> {
        void setPresenter(ProfileContract.Presenter presenter);
        void displayCustomDialog(String messageTitle, String message, String actionTitle);
    }

    interface Presenter extends BasePresenter {
        void showDialog();
    }
}