//Daniel Rivera
//MDV 469 - 1805
//ProfileDetailActivity.java

package com.drivera521.baccalculator.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.drivera521.baccalculator.R;
import com.drivera521.baccalculator.fragments.ProfileDetailFragment;

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
