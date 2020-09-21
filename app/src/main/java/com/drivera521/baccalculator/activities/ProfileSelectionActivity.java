//Daniel Rivera
//MDV 469 - 1805
//ProfileSelectionActivity.java

package com.drivera521.baccalculator.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.drivera521.baccalculator.R;
import com.drivera521.baccalculator.fragments.ProfileSelectionFragment;

public class ProfileSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_layout);


        getFragmentManager().beginTransaction().replace(R.id.profile_select_frame, ProfileSelectionFragment.newInstance()).commit();

    }

    @Override
    protected void onResume() {
        super.onResume();

        getFragmentManager().beginTransaction().replace(R.id.profile_select_frame, ProfileSelectionFragment.newInstance()).commit();
    }
}
