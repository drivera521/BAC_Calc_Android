//Daniel Rivera
//MDV 469 - 1805
//UpdateActivity.java


package com.example.daniel.riveradaniel_project1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.daniel.riveradaniel_project1.R;
import com.example.daniel.riveradaniel_project1.fragments.UpdateFragment;

public class UpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_activity_layout);

        Intent getProfileIntent = getIntent();
        int profileID = getProfileIntent.getIntExtra("PROFILE_ID",1);

        getFragmentManager().beginTransaction().replace(R.id.update_frame_container, UpdateFragment.newInstance(profileID)).commit();
    }
}
