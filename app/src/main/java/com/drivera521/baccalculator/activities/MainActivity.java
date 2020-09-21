//Daniel Rivera
//MDV 469 - 1805
//MainActivity.java


package com.drivera521.baccalculator.activities;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.drivera521.baccalculator.R;
import android.os.Bundle;
import com.drivera521.baccalculator.drink_database.DrinkContract;
import com.drivera521.baccalculator.drink_database.DrinkData;
import com.drivera521.baccalculator.drink_database.DrinkDatabaseHelper;
import com.drivera521.baccalculator.fragments.MainScreenFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<DrinkData> setDrinkData;
    private final DrinkDatabaseHelper db = new DrinkDatabaseHelper(this);

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        verifyPermissions();

    }

    private void setupDrinkDatabase() {

        addData();


        ContentValues cv = new ContentValues();

        SQLiteDatabase sq = db.getWritableDatabase();
        for (DrinkData drink : setDrinkData) {

            cv.put(DrinkContract.DRINKNAME, drink.getDrinkName());
            cv.put(DrinkContract.STANDARDDRINK, drink.getStandardDrink());
            cv.put(DrinkContract.DRINKVOLUME, drink.getDrinkVolume());
            cv.put(DrinkContract.DRINKPERCENTAGE, drink.getDrinkPercentage());
            cv.put(DrinkContract.DRINKIMAGE, drink.getDrinkImage());

            if (!checkDBEntry(drink)) {
                sq.insert(DrinkContract.DATA_TABLE, null, cv);
            }
        }


    }

    private void addData() {

        DrinkData drink1 = new DrinkData("Beer", "1", "12", "5", String.valueOf(R.drawable.beer_image));

        DrinkData drink2 = new DrinkData("Martini", "1.5", "2.25", "37.3", String.valueOf(R.drawable.martini_image));
        DrinkData drink3 = new DrinkData("Pina Colada", "2", "9", "13.3", String.valueOf(R.drawable.colada_icon));
        DrinkData drink4 = new DrinkData("Margarita", "1.7", "3", "33.3", String.valueOf(R.drawable.margarita));
        DrinkData drink5 = new DrinkData("Bourbon and Water", "1.3", "6", "13.3", String.valueOf(R.drawable.tumbler_icon));

        DrinkData drink6 = new DrinkData("Mixed Drink", "1.3", "6", "13.3", String.valueOf(R.drawable.mixed_drink));
        DrinkData drink7 = new DrinkData("Shot", "1", "1", "40", String.valueOf(R.drawable.whiskey));

        DrinkData drink8 = new DrinkData("Wine", "1.4", "5", "11.6", String.valueOf(R.drawable.wine_image));
        setDrinkData.add(drink1);
        setDrinkData.add(drink2);
        setDrinkData.add(drink3);
        setDrinkData.add(drink4);
        setDrinkData.add(drink5);
        setDrinkData.add(drink6);
        setDrinkData.add(drink7);
        setDrinkData.add(drink8);

    }

    private boolean checkDBEntry(DrinkData currentDrink) {

        String drinkName = currentDrink.getDrinkName();

        SQLiteDatabase sq = db.getReadableDatabase();

        try {
            Cursor mCursor = sq.query(DrinkContract.DATA_TABLE, null, null, null, null, null, null);

            mCursor.moveToFirst();

            while (!mCursor.isAfterLast()) {
                String name = mCursor.getString(mCursor.getColumnIndex(DrinkContract.DRINKNAME));

                if (name.equals(drinkName)) {
                    return true;
                }
                mCursor.moveToNext();
            }
            mCursor.close();
        } catch (SQLiteException e) {
            //no database
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        verifyPermissions();
    }

    private void verifyPermissions(){

        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[0]) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[1]) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this.getApplicationContext(),permissions[2])==PackageManager.PERMISSION_GRANTED) {

            setDrinkData = new ArrayList<>();
            setupDrinkDatabase();

            getFragmentManager().beginTransaction().replace(R.id.main_screen_fragment_container, MainScreenFragment.newInstance()).commit();

        } else {

            ActivityCompat.requestPermissions(this,permissions,REQUEST_CODE_ASK_PERMISSIONS);
        }
    }
}
