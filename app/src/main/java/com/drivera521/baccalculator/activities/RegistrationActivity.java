//Daniel Rivera
//MDV 469 - 1805
//RegistrationActivity.java

package com.drivera521.baccalculator.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.drivera521.baccalculator.R;
import com.drivera521.baccalculator.fragments.RegistrationFragment;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity_layout);

        getFragmentManager().beginTransaction().replace(R.id.registration_frame_container, RegistrationFragment.newInstance()).commit();
    }
}
