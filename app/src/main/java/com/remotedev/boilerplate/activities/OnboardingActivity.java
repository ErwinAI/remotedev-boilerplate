package com.remotedev.boilerplate.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.remotedev.boilerplate.R;
import com.remotedev.boilerplate.enums.PreferenceEnum;
import com.remotedev.boilerplate.fragments.OnboardingFragment;
import com.remotedev.boilerplate.utils.GlobalUtils;
import com.remotedev.boilerplate.variables.PreferenceVariables;

/**
 * Onboarding Activity class
 *
 * Created by Erwin on 05/03/2018.
 */
public class OnboardingActivity extends _BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, View.OnTouchListener {
    private final String TAG = getClass().getSimpleName();

    // Version of the onboarding.
    // If you have added new screens, increase this number by one.
    public static final int ONBOARDING_VERSION = 1;

    // Amount of slides in the onboarding.
    // Increase when more pages must be added
    private final int PAGE_AMOUNT = 3;

    private OnboardingPagerAdapter mSectionsPagerAdapter;
    private ViewPager viewPager;
    private List<ImageView> pagerList;
    private LinearLayout pagerStrip;
    private Button actionButtonFinish, actionButtonContinue;

    /**
     * onCreate
     * @param savedInstanceState savedInstanceState
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_onboarding);

        // init views
        HorizontalScrollView scrollView = findViewById(R.id.horizontal_sv);
        viewPager = findViewById(R.id.viewpager);
        pagerStrip = findViewById(R.id.pager_strip);
        actionButtonFinish = findViewById(R.id.action_onboarding_finish);
        actionButtonContinue = findViewById(R.id.action_onboarding_continue);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new OnboardingPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager.setAdapter(mSectionsPagerAdapter);
        viewPager.addOnPageChangeListener(this);
        viewPager.setPageTransformer(true, new OnboardingPageTransformer());

        // create the dots (pager tab strip)
        fillPagerStrip();

        // set listeners
        actionButtonFinish.setOnClickListener(this);
        actionButtonContinue.setOnClickListener(this);
        scrollView.setOnTouchListener(this);
    }

    /**
     * For each page in the adapter, add an image view with a background to show progress
     */
    private void fillPagerStrip() {
        pagerList = new ArrayList<>();
        int margins = GlobalUtils.dipToPixels(getApplicationContext(), 2);

        if(mSectionsPagerAdapter.getCount() > 1) {
            for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
                AppCompatImageView img = new AppCompatImageView(getApplicationContext());

                if (i == 0) {
                    img.setImageResource(R.drawable.viewpager_paging_selected);
                } else {
                    img.setImageResource(R.drawable.viewpager_paging_unselected);
                }

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(margins, 0, margins, 0);
                img.setLayoutParams(lp);

                pagerList.add(img);
                pagerStrip.addView(img);
            }
        } else {
            pagerStrip.setVisibility(View.GONE);
        }
    }

    /**
     * onClick events
     * @param v view
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action_onboarding_finish:
                // set the onboarding preference to the current onboarding value
                PreferenceVariables.setIntPreference(PreferenceEnum.ONBOARDING_VERSION_SEEN, ONBOARDING_VERSION, getApplicationContext());

                //finish the page
                finish();
                break;
            case R.id.action_onboarding_continue:
                viewPager.arrowScroll(View.FOCUS_RIGHT);
        }
    }

    /**
     * disable the scrollview
     * @param view view
     * @param motionEvent motionEvent
     * @return boolean
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }

    /**
     * Does nothing but is needed
     * @param position position
     * @param positionOffset positionOffset
     * @param positionOffsetPixels positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    /**
     * When a new page is selected, set the pager icon and set the buttons based on position
     * @param position position
     */
    @Override
    public void onPageSelected(int position) {
        // set current pager icon
        for (int i = 0; i < pagerList.size(); i++) {
            if(i == position) {
                pagerList.get(i).setImageResource(R.drawable.viewpager_paging_selected);
            } else {
                pagerList.get(i).setImageResource(R.drawable.viewpager_paging_unselected);
            }
        }

        //if last page, switch buttons around
        if(position == mSectionsPagerAdapter.getCount()-1) {
            actionButtonFinish.setVisibility(View.VISIBLE);
            actionButtonContinue.setVisibility(View.GONE);
        } else {
            //if not, switch them back
            actionButtonFinish.setVisibility(View.GONE);
            actionButtonContinue.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Does nothing but is needed
     * @param state state
     */
    @Override
    public void onPageScrollStateChanged(int state) {}

    /**
     * Viewpager Adapter
     */
    public class OnboardingPagerAdapter extends FragmentPagerAdapter {

        public OnboardingPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return OnboardingFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return PAGE_AMOUNT;
        }
    }

    /**
     * OnboardingPageTransformer
     */
    public class OnboardingPageTransformer implements ViewPager.PageTransformer {

        public void transformPage(@NonNull View view, float position) {
            int pageWidth = view.getWidth();
            TextView content = view.findViewById(R.id.onboarding_content);
            ImageView image = view.findViewById(R.id.onboarding_image);

            if (position <= 1) {
                content.setTranslationX((position) * (pageWidth / 5f));
                image.setTranslationX((position) * (pageWidth / 5f));
            }
        }
    }
}
