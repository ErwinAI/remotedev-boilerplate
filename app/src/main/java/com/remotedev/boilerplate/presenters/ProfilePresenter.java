package com.remotedev.boilerplate.presenters;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.VolleyError;
import com.crashlytics.android.Crashlytics;

import com.remotedev.boilerplate.R;
import com.remotedev.boilerplate.contracts.ProfileContract;
import com.remotedev.boilerplate.interfaces.VolleyCallback;
import com.remotedev.boilerplate.network.NetworkManager;
import com.remotedev.boilerplate.variables.NetworkVariables;

import static com.remotedev.boilerplate._BaseApplication.getAppContext;

/**
 * ProfilePresenter
 *
 * Created by Erwin on 05-07-20/2018.
 */
public class ProfilePresenter implements ProfileContract.Presenter {
    private String TAG = getClass().getSimpleName();
    private final ProfileContract.View mView;
    private Context mContext;

    public ProfilePresenter(ProfileContract.View view, Context context) {
        mView = view;
        mContext = context;
        mView.setPresenter(this);
    }

    /**
     * Sample function that shows a dialog depending on the response from a fake api.
     *
     * TODO: of course the strings should not be hardcoded but obtained like so:
     * mContext.getResources().getString(R.string.something)
     */
    @Override
    public void showDialog() {
        //First Api call to log out the user from the company server to logout the user
        NetworkManager.getInstance(getAppContext()).getCall(NetworkVariables.getSampleUrl(), "", new VolleyCallback<String>() {
            @Override
            public void getResult(String response, VolleyError error, String errorMessage) {
                // response is not empty, process it
                if (!TextUtils.isEmpty(response)) {
                    //If logOut call is successful we redirect the user to the pinCode screen
                    mView.displayCustomDialog("Hooray!","Call was successful.","Okay");
                } else {
                    // Check for error in response and show it to user if there is
                    if (error != null && error.networkResponse != null && error.networkResponse.statusCode == 401) {
                        mView.displayCustomDialog("Boo!","Call was not successful, no authorization","Okay");
                        Log.d(TAG, "User not authorized");
                        Crashlytics.log(Log.WARN, TAG, "User is not authorized, 401 given.");
                    } else {
                        if (!errorMessage.isEmpty()) {
                            mView.displayCustomDialog("Boo!","Call was not successful","Okay");
                            Log.d(TAG, errorMessage);
                            Crashlytics.log(Log.ERROR, TAG, errorMessage);
                        }
                    }
                }
            }
        });

    }
}
