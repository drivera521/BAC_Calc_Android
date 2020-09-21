//Daniel Rivera
//MDV 469 - 1805
//UpdateActivity.java


package com.drivera521.baccalculator.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.drivera521.baccalculator.R;
import com.drivera521.baccalculator.fragments.UpdateFragment;

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
