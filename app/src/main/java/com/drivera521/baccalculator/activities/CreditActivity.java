//Daniel Rivera
//MDV 469 - 1805
//CreditActivity.java

package com.drivera521.baccalculator.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.drivera521.baccalculator.R;
import com.drivera521.baccalculator.fragments.CreditFragment;

public class CreditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_activity_layout);

        getFragmentManager().beginTransaction().replace(R.id.credit_frame, CreditFragment.newInstance()).commit();
    }
}
