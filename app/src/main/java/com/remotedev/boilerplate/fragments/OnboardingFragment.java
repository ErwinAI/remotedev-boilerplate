package com.remotedev.boilerplate.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.remotedev.boilerplate.R;

/**
 * <b>OnboardingFragment class</b>
 *
 * Created by Erwin on 05/03/2018.
 */
public class OnboardingFragment extends Fragment {
    private static final String POSITION = "position";

    private int position;

    public OnboardingFragment() {
        // Required empty public constructor
    }

    /**
     * Method to create a new instance class with title and content.
     */
    public static OnboardingFragment newInstance(int pos) {
        OnboardingFragment fragment = new OnboardingFragment();
        Bundle args = new Bundle();

        args.putInt(POSITION, pos);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * onCreate method
     * @param savedInstanceState savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(POSITION);
        }
    }

    /**
     * onCreateView method
     * @param inflater inflater
     * @param container container
     * @param savedInstanceState savedInstanceState
     * @return View
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding, container, false);
    }

    /**
     * onViewCreated method
     * @param view view
     * @param savedInstanceState savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // obtain title and content view.
        ImageView image = view.findViewById(R.id.onboarding_image);
        TextView title = view.findViewById(R.id.onboarding_title);
        TextView content = view.findViewById(R.id.onboarding_content);

        // set details based on position
        switch (position) {
            default:
            case 0:
                image.setImageResource(R.mipmap.img_onboarding_example);
                title.setText(getString(R.string.onboarding_page_one_title));
                content.setText(getString(R.string.onboarding_page_one_text));
                break;
            case 1:
                image.setImageResource(R.mipmap.img_onboarding_example);
                title.setText(getString(R.string.onboarding_page_two_title));
                content.setText(getString(R.string.onboarding_page_two_text));
                break;
            case 2:
                image.setImageResource(R.mipmap.img_onboarding_example);
                title.setText(getString(R.string.onboarding_page_three_title));
                content.setText(getString(R.string.onboarding_page_three_text));
                break;
        }

    }
}
