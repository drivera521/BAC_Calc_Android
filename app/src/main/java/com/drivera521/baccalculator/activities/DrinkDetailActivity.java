//Daniel Rivera
//MDV 469 - 1805
//DrinkDetailActivity.java


package com.example.daniel.riveradaniel_project1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.daniel.riveradaniel_project1.R;
import com.example.daniel.riveradaniel_project1.fragments.DrinkDetailFragment;

import java.util.ArrayList;

public class DrinkDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drink_detail_activity);

        Intent getData = getIntent();
        int profileId = getData.getIntExtra("PROFILE_ID",1);
        int drinkID = getData.getIntExtra("DRINK_ID",1);
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(profileId);
        ids.add(drinkID);

        getFragmentManager().beginTransaction().replace(R.id.drink_detail_frame_container,DrinkDetailFragment.newInstance(ids)).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}
