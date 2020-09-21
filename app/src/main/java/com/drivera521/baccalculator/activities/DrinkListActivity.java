//Daniel Rivera
//MDV 469 - 1805
//DrinkListActivity.java
package com.drivera521.baccalculator.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.drivera521.baccalculator.R;
import com.drivera521.baccalculator.fragments.DrinkListFragment;

public class DrinkListActivity extends AppCompatActivity {

    private int profileID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drink_list_activity_layout);


        Intent getProfile = getIntent();
        profileID = getProfile.getIntExtra("PROFILE_ID",1);


        getFragmentManager().beginTransaction().replace(R.id.drink_list_frame_container, DrinkListFragment.newInstance(profileID)).commit();

    }

    @Override
    protected void onResume() {
        super.onResume();


        getFragmentManager().beginTransaction().replace(R.id.drink_list_frame_container, DrinkListFragment.newInstance(profileID)).commit();

    }
}
