//Daniel Rivera
//MDV 469 - 1805
//BACDisplayActivity.java

package com.example.daniel.riveradaniel_project1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.daniel.riveradaniel_project1.R;
import com.example.daniel.riveradaniel_project1.fragments.BACDisplayFragment;

public class BACDisplayActivity extends AppCompatActivity {

    private int profileID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bac_activity_layout);

        Intent getProfile = getIntent();

        profileID = getProfile.getIntExtra("PROFILE_ID",1);

        getFragmentManager().beginTransaction().replace(R.id.bac_frame_container, BACDisplayFragment.newInstance(profileID)).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
