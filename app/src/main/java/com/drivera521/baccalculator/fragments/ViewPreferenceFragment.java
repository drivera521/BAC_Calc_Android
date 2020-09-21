package com.drivera521.baccalculator.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import androidx.annotation.Nullable;

import com.drivera521.baccalculator.R;

public class ViewPreferenceFragment extends PreferenceFragment {

    public static ViewPreferenceFragment newInstance() {

        return new ViewPreferenceFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.drink_list_preference);


    }



}
