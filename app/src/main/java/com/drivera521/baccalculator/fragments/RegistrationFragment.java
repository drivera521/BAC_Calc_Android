package com.drivera521.baccalculator.fragments;

import android.app.Fragment;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.drivera521.baccalculator.drink_database.Profile;
import com.drivera521.baccalculator.drink_database.ProfileContract;
import com.drivera521.baccalculator.drink_database.ProfileDatabaseHelper;
import com.drivera521.baccalculator.R;

public class RegistrationFragment extends Fragment {


    private EditText fName;
    private EditText lName;
    private EditText weight;
    private RadioButton maleRBtn;
    private RadioButton femaleRBtn;
    public static RegistrationFragment newInstance() {

        return new RegistrationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.registration_fragment_layout,container,false);

        fName = v.findViewById(R.id.first_name);
        lName = v.findViewById(R.id.update_last_name);
        weight = v.findViewById(R.id.update_weight);
        maleRBtn = v.findViewById(R.id.update_radio_male);
        femaleRBtn = v.findViewById(R.id.update_radio_female);

        Button addProfile = v.findViewById(R.id.add_btn);
        addProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String genderString;
                String firstName;
                String lastName;
                String wt;
                if (validateField(fName.getText().toString())
                        && validateField(lName.getText().toString())
                        && validateField(weight.getText().toString())){

                    if(!maleRBtn.isChecked() && !femaleRBtn.isChecked()){

                        Toast.makeText(getActivity(),"Select a gender",Toast.LENGTH_SHORT).show();
                    } else {
                        if (Integer.parseInt(weight.getText().toString()) <= 0) {
                            Toast.makeText(getActivity(), "Enter a weight value greater than zero", Toast.LENGTH_SHORT).show();
                        } else {
                            Profile newUser = new Profile();

                            firstName = fName.getText().toString();
                            lastName = lName.getText().toString();
                            String fullName = firstName + " " + lastName;
                            wt = weight.getText().toString();

                            if (maleRBtn.isChecked()) {
                                genderString = "male";

                            } else {
                                genderString = "female";
                            }

                            ProfileDatabaseHelper db = new ProfileDatabaseHelper(getActivity());
                            SQLiteDatabase sq = db.getWritableDatabase();

                            //                      int weightValue = Integer.parseInt(wt);
                            ContentValues cv = new ContentValues();
                            cv.put(ProfileContract.USERNAME, fullName);
                            cv.put(ProfileContract.USERWEIGHT, wt);
                            cv.put(ProfileContract.GENDER, genderString);

                            sq.insert(ProfileContract.DATA_TABLE, null, cv);
                            getActivity().finish();
                        }
                    }
                }

            }
        });

        Button cancel = v.findViewById(R.id.update_cancel_btn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return v;
    }

    private boolean validateField(String string){

        if (string.isEmpty()||string.trim().equals("")){
            Toast.makeText(getActivity(),"Please ensure that all fields are filled out",Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}
