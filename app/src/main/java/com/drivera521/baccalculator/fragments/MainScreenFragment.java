//Daniel Rivera
//MDV469 - O
//MainScreenFragment.java

package com.drivera521.baccalculator.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.drivera521.baccalculator.activities.CreditActivity;
import com.drivera521.baccalculator.activities.ProfileSelectionActivity;
import com.drivera521.baccalculator.R;

public class MainScreenFragment extends Fragment {

    public static MainScreenFragment newInstance() {

        return new MainScreenFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_screen_fragment_layout,container,false);

        Button startBtn = v.findViewById(R.id.start_btn);
        Button creditBtn = v.findViewById(R.id.credit_btn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getActivity(),ProfileSelectionActivity.class);
                startActivity(startIntent);
            }
        });

        creditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent creditIntent = new Intent(getActivity(), CreditActivity.class);
                startActivity(creditIntent);
            }
        });

        return v;
    }
}
