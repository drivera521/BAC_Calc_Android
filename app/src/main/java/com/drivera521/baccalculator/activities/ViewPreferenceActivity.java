//Daniel Rivera
//MDV 469 - 1805
//ViewPreferenceActivity.java

package com.drivera521.baccalculator.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.drivera521.baccalculator.R;
import com.drivera521.baccalculator.fragments.ViewPreferenceFragment;

public class ViewPreferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference_activity_layout);

        getFragmentManager().beginTransaction().replace(R.id.preference_frame_container, ViewPreferenceFragment.newInstance()).commit();
    }

}
