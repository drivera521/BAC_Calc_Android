package com.drivera521.baccalculator.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.drivera521.baccalculator.BACListAdapter;
import com.drivera521.baccalculator.drink_database.ProfileContract;
import com.drivera521.baccalculator.drink_database.ProfileDatabaseHelper;
import com.drivera521.baccalculator.R;
import com.drivera521.baccalculator.drink_database.DrinkContract;
import com.drivera521.baccalculator.drink_database.DrinkDatabaseHelper;
import com.drivera521.baccalculator.drink_database.DrinkLog;
import com.drivera521.baccalculator.drink_database.DrinkLogContract;
import com.drivera521.baccalculator.drink_database.DrinkLogDatabase;

import java.util.ArrayList;
import java.util.Locale;

public class BACDisplayFragment extends Fragment {

    private int profileID;
    private ListView drinkLogList;
    private TextView bacLvlTV;
    private TextView drinkListLbl;
    private TextView timeToSober;
    private boolean isProfileMale;
    private String profileName;
    private int profileWeight;
    private final double maleConstant = .68;
    private final double femaleConstant = .55;
    private ArrayList<DrinkLog> drinkingLog;
    private int stdDrinkTotal = 0;




    public static BACDisplayFragment newInstance(int profileID) {

        Bundle args = new Bundle();
        args.putInt("PROFILE_ID", profileID);
        BACDisplayFragment fragment = new BACDisplayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bac_fragment_layout,container,false);

        drinkingLog = new ArrayList<>();
        profileID = getArguments().getInt("PROFILE_ID");
        bacLvlTV = v.findViewById(R.id.bac_level_text);
        drinkLogList = v.findViewById(R.id.bac_drink_listview);
        drinkListLbl = v.findViewById(R.id.bac_drink_list_label);
        timeToSober = v.findViewById(R.id.time_to_sober);

        getUserInfo();
        double bacResult = calculateBAC();
        String result = String.format(Locale.US,"%1$.3f",bacResult);
        bacLvlTV.setText(result);

        if (bacResult < 0.04){
            bacLvlTV.setTextColor(getResources().getColor(R.color.grreenBAC));
        } else if (bacResult < 0.08){
            bacLvlTV.setTextColor(getResources().getColor(R.color.orangeBAC));
        } else {
            bacLvlTV.setTextColor(getResources().getColor(R.color.redBAC));
        }

        BACListAdapter adapter = new BACListAdapter(getActivity(),drinkingLog);
        drinkLogList.setAdapter(adapter);

        String soberText = "";
        drinkListLbl.setText(profileName + "'s Drink List");

        if (stdDrinkTotal == 1){
            soberText = "You have " + stdDrinkTotal + " hour to have a zero BAC reading";;
        } else {
            soberText = "You have " + stdDrinkTotal + " hours to have a zero BAC reading";;
        }



        timeToSober.setText(soberText);



        return v;
    }

    private double calculateBAC(){
        if (!drinkingLog.isEmpty()) {
            double genderConstant;
            double numStdDrinks = 0.0;
            long startTimeMilliSeconds = System.currentTimeMillis();
            long lastTimeMilliSeconds = System.currentTimeMillis();

            int hoursPassed;
            int adjWeight = profileWeight * 424;


            if (isProfileMale) {
                genderConstant = maleConstant;
            } else {
                genderConstant = femaleConstant;
            }

            for (DrinkLog drink : drinkingLog) {
                numStdDrinks += Double.valueOf(drink.getStdDrink());
                long drinkTimeMilliSeconds = Long.valueOf(drink.getConsumedTime());
                if (drinkTimeMilliSeconds <= startTimeMilliSeconds) {
                    startTimeMilliSeconds = drinkTimeMilliSeconds;
                }
            }

            long timeDifferenceMilliSeconds = lastTimeMilliSeconds - startTimeMilliSeconds;

            hoursPassed = (int) (timeDifferenceMilliSeconds / (3600 * 1000));

            if (hoursPassed == 0) {
                hoursPassed = 1;
            }

            double abv = (numStdDrinks * 14) / (genderConstant * adjWeight);
            double bacLevel = (abv * 100) - (hoursPassed * .015);

            if (bacLevel < 0){
                bacLevel = 0;
            }
            return bacLevel;
        }

        return 0.0;

    }


    private void getUserInfo(){

        ProfileDatabaseHelper pDB  = new ProfileDatabaseHelper(getActivity());
        SQLiteDatabase pSQ = pDB.getReadableDatabase();

        Cursor pCursor = pSQ.query(ProfileContract.DATA_TABLE,null,null,null,null,null,null);

        pCursor.moveToFirst();

        while(!pCursor.isAfterLast()){
            int intUserID = Integer.parseInt(pCursor.getString(pCursor.getColumnIndex(ProfileContract.ID)));
            if (intUserID == profileID){
                profileName = pCursor.getString(pCursor.getColumnIndex(ProfileContract.USERNAME));
                profileWeight = Integer.parseInt(pCursor.getString(pCursor.getColumnIndex(ProfileContract.USERWEIGHT)));
                String gender = pCursor.getString(pCursor.getColumnIndex(ProfileContract.GENDER)).toLowerCase();

                isProfileMale = gender.equals("male");
                break;

            }

            pCursor.moveToNext();
        }
        pCursor.close();


        DrinkLogDatabase dDB = new DrinkLogDatabase(getActivity());
        SQLiteDatabase dSQ = dDB.getReadableDatabase();

        DrinkDatabaseHelper drinkDB = new DrinkDatabaseHelper(getActivity());
        SQLiteDatabase drinkSQ = drinkDB.getReadableDatabase();

        Cursor dCursor = dSQ.query(DrinkLogContract.DATA_TABLE,null,null,null,null,null,null);
        Cursor drinkCursor = drinkSQ.query(DrinkContract.DATA_TABLE,null,null,null,null,null,null);

        dCursor.moveToFirst();

        while (!dCursor.isAfterLast()){
            int intUserID = Integer.parseInt(dCursor.getString(dCursor.getColumnIndex(DrinkLogContract.USERID)));
            if(profileID == intUserID){
                String drinkID = dCursor.getString(dCursor.getColumnIndex(DrinkLogContract.DRINKID));
                String drinkTime = dCursor.getString(dCursor.getColumnIndex(DrinkLogContract.DRINKTIME));

                long drinkTimeMilliseconds = Long.valueOf(drinkTime);
                long timeFilter = System.currentTimeMillis() - 43200000;
                if (drinkTimeMilliseconds > timeFilter) {
                    drinkCursor.moveToFirst();
                    while (!drinkCursor.isAfterLast()) {

                        String tempDrinkID = drinkCursor.getString(drinkCursor.getColumnIndex(DrinkContract.ID));
                        if (tempDrinkID.equals(drinkID)) {

                            String tempDrinkName = drinkCursor.getString(drinkCursor.getColumnIndex(DrinkContract.DRINKNAME));
                            String tempStdDrinks = drinkCursor.getString(drinkCursor.getColumnIndex(DrinkContract.STANDARDDRINK));

                            double temptStdDrink = Double.valueOf(tempStdDrinks);
                            stdDrinkTotal += (int) temptStdDrink;
                            DrinkLog currentDrink = new DrinkLog(tempDrinkName, tempStdDrinks, drinkTime);
                            drinkingLog.add(currentDrink);

                        }
                        drinkCursor.moveToNext();
                    }
                }

            }
            dCursor.moveToNext();
        }

        drinkCursor.close();
        dCursor.close();
    }

}
