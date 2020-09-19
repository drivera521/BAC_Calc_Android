package com.example.daniel.riveradaniel_project1.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.daniel.riveradaniel_project1.R;

public class CreditFragment extends Fragment {

    public static CreditFragment newInstance() {
        return new CreditFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.credit_fragment_layout,container,false);
    }
}
