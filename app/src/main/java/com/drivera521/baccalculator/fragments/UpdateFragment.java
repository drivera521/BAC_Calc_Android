package com.drivera521.baccalculator.fragments;

import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
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

public class UpdateFragment extends Fragment {


    private EditText fName;
    private EditText lName;
    private EditText weight;
    private RadioButton maleRBtn;
    private RadioButton femaleRBtn;
    private int profileID;

    private String collectedFirstName;
    private String collectedLastName;
    private String collectedWeight;
    private String collectedGender;

    public static UpdateFragment newInstance(int profileID) {

        Bundle args = new Bundle();

        args.putInt("PROFILE_ID",profileID);
        UpdateFragment fragment = new UpdateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.update_fragment_layout,container,false);

        profileID = getArguments().getInt("PROFILE_ID");



        fName = v.findViewById(R.id.update_first_name);
        lName = v.findViewById(R.id.update_last_name);
        weight = v.findViewById(R.id.update_weight);
        maleRBtn = v.findViewById(R.id.update_radio_male);
        femaleRBtn = v.findViewById(R.id.update_radio_female);

        getProfileValues();

        fName.setText(collectedFirstName);
        lName.setText(collectedLastName);
        weight.setText(collectedWeight);
        if(collectedGender.toLowerCase().equals("male")){
            maleRBtn.setChecked(true);
            femaleRBtn.setChecked(false);
        } else {
            maleRBtn.setChecked(false);
            femaleRBtn.setChecked(true);
        }

        Button updateProfile = v.findViewById(R.id.update_btn);
        updateProfile.setOnClickListener(new View.OnClickListener() {
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
                        cv.put(ProfileContract.USERNAME, fullName );
                        cv.put(ProfileContract.USERWEIGHT,wt);
                        cv.put(ProfileContract.GENDER,genderString);

                        sq.update(ProfileContract.DATA_TABLE,cv,"_ID = ?",new String[] { String.valueOf(profileID)});
                        getActivity().finish();
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

    private void getProfileValues(){

        ProfileDatabaseHelper db = new ProfileDatabaseHelper(getActivity());
        SQLiteDatabase sq = db.getReadableDatabase();

        Cursor mCursor = sq.query(ProfileContract.DATA_TABLE,null,null,null,null,null,null);

        mCursor.moveToFirst();

        while (!mCursor.isAfterLast()){
            int currentID = Integer.parseInt(mCursor.getString(mCursor.getColumnIndex(ProfileContract.ID)));

            if (currentID == profileID){

                String name = mCursor.getString(mCursor.getColumnIndex(ProfileContract.USERNAME));
                String[] nameArray = name.split(" ");
                collectedFirstName = nameArray[0];
                collectedLastName = nameArray[1];
                collectedWeight = mCursor.getString(mCursor.getColumnIndex(ProfileContract.USERWEIGHT));
                collectedGender = mCursor.getString(mCursor.getColumnIndex(ProfileContract.GENDER));

            }
            mCursor.moveToNext();
        }
        mCursor.close();

    }
}
