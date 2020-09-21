package com.drivera521.baccalculator.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.Nullable;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.drivera521.baccalculator.CustomProfileListAdapter;
import com.drivera521.baccalculator.drink_database.ProfileContract;
import com.drivera521.baccalculator.drink_database.ProfileDatabaseHelper;
import com.drivera521.baccalculator.activities.ProfileDetailActivity;
import com.drivera521.baccalculator.R;
import com.drivera521.baccalculator.activities.RegistrationActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProfileSelectionFragment extends Fragment {

    private ArrayList<String> profileNames;
    private ArrayList<Integer> idNumbers;
    public static ProfileSelectionFragment newInstance() {
        return new ProfileSelectionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile_selection_fragment_layout,container,false);
        ProfileDatabaseHelper db = new ProfileDatabaseHelper(getActivity());
        SQLiteDatabase sq = db.getReadableDatabase();
        profileNames = new ArrayList<>();
        idNumbers = new ArrayList<>();

        Cursor mCursor = sq.rawQuery("SELECT * FROM " + ProfileContract.DATA_TABLE, null);

        if(mCursor.getCount() > 0) {

            mCursor.moveToFirst();

            while(!mCursor.isAfterLast()){

                String name = mCursor.getString(mCursor.getColumnIndex(ProfileContract.USERNAME));
                int idValue = Integer.parseInt(mCursor.getString(mCursor.getColumnIndex(ProfileContract.ID)));
                profileNames.add(name);
                idNumbers.add(idValue);

                mCursor.moveToNext();
            }



            ListView lv = v.findViewById(R.id.profile_list_view);

            CustomProfileListAdapter adapter = new CustomProfileListAdapter(getActivity(),profileNames);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    Intent profileDetailIntent = new Intent(getActivity(), ProfileDetailActivity.class);
                    profileDetailIntent.putExtra("PROFILE_ID",idNumbers.get(position));
                    startActivity(profileDetailIntent);


                }
            });

        }

        FloatingActionButton fab = v.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(getActivity(),RegistrationActivity.class);
                startActivity(registerIntent);
            }
        });
        return v;

    }
}
