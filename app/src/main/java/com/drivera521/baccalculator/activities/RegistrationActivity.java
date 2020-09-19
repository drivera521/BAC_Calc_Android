//Daniel Rivera
//MDV 469 - 1805
//RegistrationActivity.java

package com.example.daniel.riveradaniel_project1.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.daniel.riveradaniel_project1.R;
import com.example.daniel.riveradaniel_project1.fragments.RegistrationFragment;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity_layout);

        getFragmentManager().beginTransaction().replace(R.id.registration_frame_container, RegistrationFragment.newInstance()).commit();
    }
}
