//Daniel Rivera
//MDV 469 - 1805
//ViewPreferenceActivity.java

package com.example.daniel.riveradaniel_project1.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.daniel.riveradaniel_project1.R;
import com.example.daniel.riveradaniel_project1.fragments.ViewPreferenceFragment;

public class ViewPreferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference_activity_layout);

        getFragmentManager().beginTransaction().replace(R.id.preference_frame_container, ViewPreferenceFragment.newInstance()).commit();
    }

}
