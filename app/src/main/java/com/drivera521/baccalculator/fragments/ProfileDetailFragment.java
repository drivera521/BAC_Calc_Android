package com.example.daniel.riveradaniel_project1.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.daniel.riveradaniel_project1.activities.BACDisplayActivity;
import com.example.daniel.riveradaniel_project1.activities.DrinkListActivity;
import com.example.daniel.riveradaniel_project1.drink_database.Profile;
import com.example.daniel.riveradaniel_project1.drink_database.ProfileContract;
import com.example.daniel.riveradaniel_project1.drink_database.ProfileDatabaseHelper;
import com.example.daniel.riveradaniel_project1.R;
import com.example.daniel.riveradaniel_project1.activities.UpdateActivity;
import com.example.daniel.riveradaniel_project1.drink_database.DrinkContract;
import com.example.daniel.riveradaniel_project1.drink_database.DrinkDatabaseHelper;
import com.example.daniel.riveradaniel_project1.drink_database.DrinkLogContract;
import com.example.daniel.riveradaniel_project1.drink_database.DrinkLogDatabase;

public class ProfileDetailFragment extends Fragment {


    private static final String PROFILE_ID_KEY = "PROFILE_ID_KEY";
    private int profileID;
    private Profile activeProfile;

    private TextView profileName;
    private TextView profileWeight;
    private TextView profileGender;
    private TextView cDrink;

    public static ProfileDetailFragment newInstance(int profileID) {

        Bundle args = new Bundle();

        args.putInt(PROFILE_ID_KEY,profileID);
        ProfileDetailFragment fragment = new ProfileDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.profile_detail_fragment_layout,container,false);

        setHasOptionsMenu(true);

        String currentDrink = "";
        profileID = getArguments().getInt(PROFILE_ID_KEY);

        ProfileDatabaseHelper db = new ProfileDatabaseHelper(getActivity());
        SQLiteDatabase sq = db.getReadableDatabase();

        Cursor mCursor = sq.query(ProfileContract.DATA_TABLE,null,null,null,null,null,null);

        mCursor.moveToFirst();

        while(!mCursor.isAfterLast()){
            int tempID;
            tempID = Integer.parseInt(mCursor.getString(mCursor.getColumnIndex(ProfileContract.ID)));
            if (tempID == profileID){
                String name = mCursor.getString(mCursor.getColumnIndex(ProfileContract.USERNAME));
                String weight = mCursor.getString(mCursor.getColumnIndex(ProfileContract.USERWEIGHT));
                String gender = mCursor.getString(mCursor.getColumnIndex(ProfileContract.GENDER));

                activeProfile = new Profile();
                activeProfile.setFullName(name);
                activeProfile.setGender(gender);
                activeProfile.setUser_weight(weight);
            }
            mCursor.moveToNext();
        }

        profileName = v.findViewById(R.id.profile_user_name);
        profileWeight = v.findViewById(R.id.weight_text);
        profileGender = v.findViewById(R.id.gender_text);
        cDrink = v.findViewById(R.id.drink_text);

        profileName.setText(activeProfile.getFullName());
        profileWeight.setText(activeProfile.getUser_weight());
        profileGender.setText(activeProfile.getGender());

        cDrink.setText(currentDrink);



        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        DrinkLogDatabase db = new DrinkLogDatabase(getActivity());
        SQLiteDatabase sq = db.getReadableDatabase();

        Cursor mCursor = sq.query(DrinkLogContract.DATA_TABLE,null,null,null,null,null,null);

        if (mCursor.getCount() >0) {
            mCursor.moveToFirst();

            long recentDate = 0;
            long tempDate = 0;
            String nowDrinking = "";
            String drinkID = "";

            while (!mCursor.isAfterLast()) {
                int profID = Integer.parseInt(mCursor.getString(mCursor.getColumnIndex(DrinkLogContract.USERID)));
                tempDate = Long.valueOf(mCursor.getString(mCursor.getColumnIndex(DrinkLogContract.DRINKTIME)));
                if (recentDate == 0) {
                    recentDate = tempDate;
                }
                if (profID == profileID) {

                    if (recentDate <= tempDate) {
                       recentDate = tempDate;
                       drinkID = mCursor.getString(mCursor.getColumnIndex(DrinkLogContract.DRINKID));
                    }
                }

                mCursor.moveToNext();
            }

            mCursor.close();

            nowDrinking = getDrinkName(drinkID);

            cDrink.setText(nowDrinking);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater = getActivity().getMenuInflater();

        inflater.inflate(R.menu.profile_detail_menu,menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.profile_update:

                Intent editIntent = new Intent(getActivity(), UpdateActivity.class);
                editIntent.putExtra("PROFILE_ID",profileID);
                startActivity(editIntent);
                break;


            case R.id.profile_add_drink:

                Intent drinkListIntent =  new Intent(getActivity(), DrinkListActivity.class);
                drinkListIntent.putExtra("PROFILE_ID", profileID);
                startActivity(drinkListIntent);
                break;
            case R.id.profile_bac:

                Intent userBACIntent = new Intent(getActivity(), BACDisplayActivity.class);
                userBACIntent.putExtra("PROFILE_ID",profileID);
                startActivity(userBACIntent);

                break;

            case R.id.profile_delete:


                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Delete Profile")
                        .setMessage("Do you want to delete the current active profile: " + activeProfile.getFullName())
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ProfileDatabaseHelper db = new ProfileDatabaseHelper(getActivity());
                                SQLiteDatabase sq = db.getWritableDatabase();

                                sq.delete(ProfileContract.DATA_TABLE, "_ID = " + profileID ,null);
                                getActivity().finish();

                            }
                        });

                builder.show();


                break;
        }


        return super.onOptionsItemSelected(item);
    }

    private String getDrinkName (String drinkID){
        DrinkDatabaseHelper db = new DrinkDatabaseHelper(getActivity());
        SQLiteDatabase sq = db.getReadableDatabase();

        Cursor dCursor = sq.query(DrinkContract.DATA_TABLE,null,null,null,null,null,null);

        dCursor.moveToFirst();

        while (!dCursor.isAfterLast()){
            String currentID = dCursor.getString(dCursor.getColumnIndex(DrinkContract.ID));
            if (currentID.equals(drinkID)){
                return dCursor.getString(dCursor.getColumnIndex(DrinkContract.DRINKNAME));
            }

            dCursor.moveToNext();
        }
        dCursor.close();

        return "";
    }
}
