package com.remotedev.boilerplate.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.remotedev.boilerplate.R;
import com.remotedev.boilerplate.interfaces.NavigationInteraction;

/**
 * BaseFragment
 * BaseFrament is the base class which can be extended in all other fragments.
 *
 * Created by Erwin on 19-02-2018.
 */
public abstract class _BaseFragment extends Fragment {

    // navigation interaction
    public NavigationInteraction mFragmentNavigation;

    // init views
    private TextView titleTv;

    private final String TAG = getClass().getSimpleName();

    /**
     * onAttach method
     * @param context context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //parse context to FragmentNavigation
        if (context instanceof NavigationInteraction) {
            mFragmentNavigation = (NavigationInteraction) context;
        }
    }

    /**
     * onDetach
     */
    @Override
    public void onDetach() {
        mFragmentNavigation = null;
        super.onDetach();
    }

    /**
     * Method to setup the toolbar if supported.
     *
     * @param view view
     * @param homeAsUpEnabled whether or not home as up is enabled
     */
    public Toolbar setupToolBar(View view, boolean homeAsUpEnabled) {
        //setup toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }

        ActionBar actionBar = activity != null ? activity.getSupportActionBar() : null;
        if(actionBar != null) {
            //settings for actionbar
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(homeAsUpEnabled);

            if(homeAsUpEnabled){
                actionBar.setHomeAsUpIndicator(R.drawable.chevron_left);
            }
        }

        return toolbar;
    }

    /**
     * Method to setup toolbar with a title, uses other setupToolBar function to do the rest.
     *
     * @param view view
     * @param homeAsUpEnabled whether or not home as up is enabled
     * @param title title
     */
    public Toolbar setupToolBar(View view, boolean homeAsUpEnabled, String title) {
        //setup toolbar and obtain it
        Toolbar toolbar = setupToolBar(view, homeAsUpEnabled);

        //obtain the views of title
        titleTv = toolbar.findViewById(R.id.toolbar_title);

        if(titleTv != null) {
            //set title
            titleTv.setText(title);
        }

        return toolbar;
    }

    /**
     * Method to update the toolbar title
     * @param title title to update to
     */
    public void updateToolbarTitle(String title) {
        if(titleTv != null) {
            titleTv.setText(title);
        }
    }
}