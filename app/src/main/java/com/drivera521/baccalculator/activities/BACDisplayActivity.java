//Daniel Rivera
//MDV 469 - 1805
//BACDisplayActivity.java

package com.drivera521.baccalculator.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.drivera521.baccalculator.R;
import com.drivera521.baccalculator.fragments.BACDisplayFragment;

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
