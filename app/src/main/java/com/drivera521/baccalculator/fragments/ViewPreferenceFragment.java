package com.example.daniel.riveradaniel_project1.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.example.daniel.riveradaniel_project1.R;

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
