//Daniel Rivera
//MDV 469 - 1805
//ProfileSelectionActivity.java

package com.example.daniel.riveradaniel_project1.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.daniel.riveradaniel_project1.R;
import com.example.daniel.riveradaniel_project1.fragments.ProfileSelectionFragment;

public class ProfileSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_layout);


        getFragmentManager().beginTransaction().replace(R.id.profile_select_frame, ProfileSelectionFragment.newInstance()).commit();

    }

    @Override
    protected void onResume() {
        super.onResume();

        getFragmentManager().beginTransaction().replace(R.id.profile_select_frame, ProfileSelectionFragment.newInstance()).commit();
    }
}
