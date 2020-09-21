package com.drivera521.baccalculator.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.drivera521.baccalculator.R;
import com.drivera521.baccalculator.drink_database.DrinkContract;
import com.drivera521.baccalculator.drink_database.DrinkData;
import com.drivera521.baccalculator.drink_database.DrinkDatabaseHelper;
import com.drivera521.baccalculator.drink_database.DrinkLogContract;
import com.drivera521.baccalculator.drink_database.DrinkLogDatabase;

import java.util.ArrayList;

public class DrinkDetailFragment extends Fragment {

    private int profileID;
    private int drinkID;
    private DrinkData currentDrink;


    public static DrinkDetailFragment newInstance(ArrayList<Integer> ids) {

        Bundle args = new Bundle();
        args.putIntegerArrayList("IDS",ids);
        DrinkDetailFragment fragment = new DrinkDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.drink_detail_fragment_layout,container,false);

        ArrayList<Integer> idNumbers = getArguments().getIntegerArrayList("IDS");
        profileID = idNumbers.get(0);
        drinkID = idNumbers.get(1);

        getDrinkDetails();

        TextView nameTV = v.findViewById(R.id.drink_detail_name_text);
        TextView percentTV = v.findViewById(R.id.alch_percent_text);
        TextView volumeTV = v.findViewById(R.id.detail_drink_volume);
        ImageView drinkImage = v.findViewById(R.id.detail_drink_image);
        Button addDrink = v.findViewById(R.id.add_drink_btn);
        Button cancelDrink = v.findViewById(R.id.cancel_drink_btn);


        nameTV.setText(currentDrink.getDrinkName());
        String percentString = currentDrink.getDrinkPercentage() + "%";
        String volumeString = currentDrink.getDrinkVolume() + "oz.";
        percentTV.setText(percentString);
        volumeTV.setText(volumeString);
        if(drinkID <= 8){
            int id = getActivity().getResources().getIdentifier(currentDrink.getDrinkImage(),null,null);
            drinkImage.setImageResource(id);
        } else{
            Bitmap drinkImg = BitmapFactory.decodeFile(currentDrink.getDrinkImage());
            drinkImage.setImageBitmap(drinkImg);
        }

        addDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Add Drink")
                        .setMessage("Do you wish to add " + currentDrink.getDrinkName() + " to the current drink log?")
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String timeString = String.valueOf(System.currentTimeMillis());
                                ContentValues cv = new ContentValues();

                                cv.put(DrinkLogContract.USERID,String.valueOf(profileID));
                                cv.put(DrinkLogContract.DRINKID, String.valueOf(drinkID));
                                cv.put(DrinkLogContract.DRINKTIME, timeString);

                                DrinkLogDatabase db = new DrinkLogDatabase(getActivity());
                                SQLiteDatabase sq = db.getWritableDatabase();

                                sq.insert(DrinkLogContract.DATA_TABLE,null,cv);

                                getActivity().finish();
                            }
                        });
                builder.show();

            }
        });

        cancelDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });









        return v;
    }

    private void getDrinkDetails(){
        DrinkDatabaseHelper db = new DrinkDatabaseHelper(getActivity());
        SQLiteDatabase sq = db.getReadableDatabase();

        Cursor mCursor = sq.query(DrinkContract.DATA_TABLE,null,null,null,null,null,null);
        mCursor.moveToFirst();
        while(!mCursor.isAfterLast()){
            int currentDrinkID = Integer.parseInt(mCursor.getString(mCursor.getColumnIndex(DrinkContract.ID)));

            if (currentDrinkID == drinkID) {

                String drinkName = mCursor.getString(mCursor.getColumnIndex(DrinkContract.DRINKNAME));
                String standDrink = mCursor.getString(mCursor.getColumnIndex(DrinkContract.STANDARDDRINK));
                String drinkVolume = mCursor.getString(mCursor.getColumnIndex(DrinkContract.DRINKVOLUME));
                String drinkPercent = mCursor.getString(mCursor.getColumnIndex(DrinkContract.DRINKPERCENTAGE));
                String drinkImage = mCursor.getString(mCursor.getColumnIndex(DrinkContract.DRINKIMAGE));

                currentDrink = new DrinkData(drinkName,standDrink,drinkVolume,drinkPercent,drinkImage);
            }

            mCursor.moveToNext();
        }

        mCursor.close();
    }
}
