package com.remotedev.boilerplate.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.remotedev.boilerplate.R;
import com.remotedev.boilerplate.fragments.AnyFragment;
import com.remotedev.boilerplate.fragments.ProfileFragment;
import com.remotedev.boilerplate.interfaces.NavigationInteraction;
import com.remotedev.boilerplate.navigation.FragNavController;
import com.remotedev.boilerplate.navigation.FragmentHistory;
import com.remotedev.boilerplate.utils.GlobalUtils;

/**
 * MainActivity
 * <p>
 * Created by Erwin on 18/02/2018.
 */
public class MainActivity extends _BaseActivity implements NavigationInteraction, TabLayout.OnTabSelectedListener, FragNavController.RootFragmentListener {

    private final String TAG = getClass().getSimpleName();

    /**
     * Icons for the navigation tabs
     */
    private int[] mTabIconsSelected = {
            R.mipmap.ic_start,
            R.mipmap.ic_information
    };

    /**
     * Names for the navigation tabs
     */
    private String[] TABS;

    private TabLayout bottomTabLayout;
    private FragNavController mNavController;
    private FragmentHistory fragmentHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Obtaining views
        bottomTabLayout = findViewById(R.id.bottom_navigation);
        TABS = getResources().getStringArray(R.array.main_menu);

        //Initialize tabs
        initTab();

        //Setup navigation
        fragmentHistory = new FragmentHistory();

        mNavController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.main_framelayout)
                .rootFragmentListener(this, TABS.length)
                .build();

        // Set the current tab as selected
        updateTabSelection(mNavController.getCurrentStackIndex());

        // Listener for changing tabs
        bottomTabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        recreate();
    }

    /**
     * Method to change which tab is selected
     *
     * @param currentTab currentTab
     */
    private void updateTabSelection(int currentTab) {
        for (int i = 0; i < TABS.length; i++) {
            TabLayout.Tab selectedTab = bottomTabLayout.getTabAt(i);
            assert selectedTab != null;
            if (selectedTab.getCustomView() != null) {
                if (currentTab != i) {
                    selectedTab.getCustomView().setSelected(false);
                } else {
                    selectedTab.getCustomView().setSelected(true);
                }
            }
        }
    }

    /**
     * Method to initialise tabs
     */
    private void initTab() {
        if (bottomTabLayout != null) {
            for (int i = 0; i < TABS.length; i++) {
                bottomTabLayout.addTab(bottomTabLayout.newTab());
                TabLayout.Tab tab = bottomTabLayout.getTabAt(i);
                if (tab != null) {
                    tab.setCustomView(getTabView(i));
                }

            }
        }
    }

    /**
     * Method to switch tab
     *
     * @param position position
     */
    private void switchTab(int position) {
        mNavController.switchTab(position);
    }

    /**
     * onOptionsItemSelected method
     *
     * @param item item
     * @return boolean
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

    /**
     * Method onBackPressed, handling the 'physical' back button
     */
    @Override
    public void onBackPressed() {
        if (!mNavController.isRootFragment()) {
            mNavController.popFragment();
        } else {
            if (fragmentHistory.isEmpty()) {
                super.onBackPressed();
            } else {
                if (fragmentHistory.getStackSize() > 1) {
                    int position = fragmentHistory.pop();
                    switchTab(position);
                    updateTabSelection(position);
                } else {
                    switchTab(0);
                    updateTabSelection(0);
                    fragmentHistory.emptyStack();
                }
            }
        }
    }

    /**
     * Obtain the tab view
     *
     * @param position position
     * @return View view
     */
    private View getTabView(int position) {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.view_tab_item_bottom, null);
        AppCompatImageView icon = view.findViewById(R.id.tab_icon);
        TextView text = view.findViewById(R.id.tab_text);
        icon.setImageDrawable(GlobalUtils.getAPICompatVectorDrawable(getApplicationContext(), mTabIconsSelected[position]));
        text.setText(TABS[position]);
        return view;
    }

    /**
     * invoked when the activity may be temporarily destroyed, save the instance state here
     * @param outState outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mNavController != null) {
            mNavController.onSaveInstanceState(outState);
        } else {
            Log.i(TAG, " onSaveInstanceState : " + "mNaveController == null");
        }
    }

    /**
     * Add a fragment to the stack
     *
     * @param fragment fragment
     */
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
        if (!isFinishing() && mNavController != null) {
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
     * Obtain the right fragment for the right tab
     *
     * @param index the index that the root of the stack Fragment needs to go
     * @return Fragment
     */
    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {
            case FragNavController.TAB1:
                return AnyFragment.newInstance();
            case FragNavController.TAB2:
                return AnyFragment.newInstance();
        }

        throw new IllegalStateException("Need to send an index that we know.");
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (!isFinishing() && mNavController != null) {
            fragmentHistory.push(tab.getPosition());

            switchTab(tab.getPosition());

            updateTabSelection(tab.getPosition());
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        if (!isFinishing() && mNavController != null) {
            mNavController.clearStack();

            onTabSelected(tab);
        }
    }
}