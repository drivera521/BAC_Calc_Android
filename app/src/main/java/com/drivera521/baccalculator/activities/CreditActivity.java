//Daniel Rivera
//MDV 469 - 1805
//CreditActivity.java

package com.example.daniel.riveradaniel_project1.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.daniel.riveradaniel_project1.R;
import com.example.daniel.riveradaniel_project1.fragments.CreditFragment;

public class CreditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_activity_layout);

        getFragmentManager().beginTransaction().replace(R.id.credit_frame, CreditFragment.newInstance()).commit();
    }
}
