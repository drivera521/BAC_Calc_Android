//Daniel Rivera
//MDV 469 - 1805
//DrinkListActivity.java
package com.example.daniel.riveradaniel_project1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.daniel.riveradaniel_project1.R;
import com.example.daniel.riveradaniel_project1.fragments.DrinkListFragment;

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
