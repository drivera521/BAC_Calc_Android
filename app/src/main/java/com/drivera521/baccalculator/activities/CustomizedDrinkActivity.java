//Daniel Rivera
//MDV 469 - 1805
//CustomizedDrinkActivity.java

package com.drivera521.baccalculator.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.drivera521.baccalculator.R;
import com.drivera521.baccalculator.fragments.CustomizedDrinkFragment;

public class CustomizedDrinkActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customized_drink_activity);

        getFragmentManager().beginTransaction().replace(R.id.custom_drink_frame_container, CustomizedDrinkFragment.newInstance()).commit();
    }
}
