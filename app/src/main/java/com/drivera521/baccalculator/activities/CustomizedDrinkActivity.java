//Daniel Rivera
//MDV 469 - 1805
//CustomizedDrinkActivity.java

package com.example.daniel.riveradaniel_project1.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.daniel.riveradaniel_project1.R;
import com.example.daniel.riveradaniel_project1.fragments.CustomizedDrinkFragment;

public class CustomizedDrinkActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customized_drink_activity);

        getFragmentManager().beginTransaction().replace(R.id.custom_drink_frame_container, CustomizedDrinkFragment.newInstance()).commit();
    }
}
