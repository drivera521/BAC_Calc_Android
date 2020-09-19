//Daniel Rivera
//MDV 469 - 1805
//ProfileDetailActivity.java

package com.example.daniel.riveradaniel_project1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.daniel.riveradaniel_project1.R;
import com.example.daniel.riveradaniel_project1.fragments.ProfileDetailFragment;

public class ProfileDetailActivity extends AppCompatActivity {

    private int profileID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_detail_activity_layout);

        Intent getId = getIntent();
        profileID = getId.getIntExtra("PROFILE_ID",1);
        getFragmentManager().beginTransaction().replace(R.id.profile_detail_frame_container, ProfileDetailFragment.newInstance(profileID)).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getFragmentManager().beginTransaction().replace(R.id.profile_detail_frame_container, ProfileDetailFragment.newInstance(profileID)).commit();
    }
}
