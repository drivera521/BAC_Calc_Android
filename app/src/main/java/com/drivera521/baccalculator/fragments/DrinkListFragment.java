package com.example.daniel.riveradaniel_project1.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;


import com.example.daniel.riveradaniel_project1.activities.CustomizedDrinkActivity;
import com.example.daniel.riveradaniel_project1.activities.DrinkDetailActivity;
import com.example.daniel.riveradaniel_project1.DrinkGridAdapter;
import com.example.daniel.riveradaniel_project1.R;
import com.example.daniel.riveradaniel_project1.activities.ViewPreferenceActivity;
import com.example.daniel.riveradaniel_project1.drink_database.DrinkContract;
import com.example.daniel.riveradaniel_project1.drink_database.DrinkData;
import com.example.daniel.riveradaniel_project1.drink_database.DrinkDatabaseHelper;

import java.util.ArrayList;

public class DrinkListFragment extends Fragment {

    private int profileID;
    private GridView drinkGridView;
    private ListView drinkListView;
    private SharedPreferences pref;
    private ArrayList<DrinkData> drinkList;
    private ArrayList<Integer> drinkID;

    public static DrinkListFragment newInstance(int profileID) {

        Bundle args = new Bundle();

        args.putInt("PROFILE_ID",profileID);
        DrinkListFragment fragment = new DrinkListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.drink_list_fragment_layout,container,false);

        setHasOptionsMenu(true);

        profileID = getArguments().getInt("PROFILE_ID");
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        createDrinkArray();

        int viewOption = Integer.parseInt(pref.getString(getString(R.string.pref_key),"0"));
        drinkGridView = v.findViewById(R.id.drink_list_gridview);
        drinkListView = v.findViewById(R.id.drink_list_view);

        DrinkGridAdapter adapter = new DrinkGridAdapter(getActivity(), drinkList,viewOption);

        if (viewOption == 0) {

            drinkListView.setVisibility(View.INVISIBLE);
            drinkGridView.setVisibility(View.VISIBLE);
            drinkGridView.setAdapter(adapter);
            drinkListView.setAdapter(adapter);

        } else {

            drinkListView.setVisibility(View.VISIBLE);
            drinkGridView.setVisibility(View.INVISIBLE);

            drinkListView.setAdapter(adapter);
            drinkGridView.setAdapter(adapter);
        }

        drinkGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                getDetailPage(position);
            }
        });

        drinkListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                getDetailPage(position);
            }
        });
        return v;
    }

    private void createDrinkArray(){

        drinkList = new ArrayList<>();
        drinkID = new ArrayList<>();

        DrinkDatabaseHelper db = new DrinkDatabaseHelper(getActivity());
        SQLiteDatabase sq = db.getReadableDatabase();

        Cursor mCursor = sq.query(DrinkContract.DATA_TABLE,null,null,null,null,null,null);

        mCursor.moveToFirst();

        while(!mCursor.isAfterLast()){
            String drinkName = mCursor.getString(mCursor.getColumnIndex(DrinkContract.DRINKNAME));
            String standardDrinks = mCursor.getString(mCursor.getColumnIndex(DrinkContract.STANDARDDRINK));
            String drinkVolume = mCursor.getString(mCursor.getColumnIndex(DrinkContract.DRINKVOLUME));
            String drinkPercentage = mCursor.getString(mCursor.getColumnIndex(DrinkContract.DRINKPERCENTAGE));
            String drinkImage = mCursor.getString(mCursor.getColumnIndex(DrinkContract.DRINKIMAGE));

            DrinkData currentDrink = new DrinkData(drinkName,standardDrinks,drinkVolume,drinkPercentage,drinkImage);

            int currentDrinkID = Integer.parseInt(mCursor.getString(mCursor.getColumnIndex(DrinkContract.ID)));
            drinkList.add(currentDrink);
            drinkID.add(currentDrinkID);

            mCursor.moveToNext();
        }
        mCursor.close();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater = getActivity().getMenuInflater();

        inflater.inflate(R.menu.drink_list_menu,menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.add_custom_drink:
                Intent customDrinkIntent = new Intent(getActivity(), CustomizedDrinkActivity.class);
                startActivity(customDrinkIntent);
                break;
            case R.id.view_preference:

                Intent preferenceIntent = new Intent(getActivity(), ViewPreferenceActivity.class);
                startActivity(preferenceIntent);
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    private void getDetailPage(int position){

        int currentDrinkID = drinkID.get(position);

        Intent drinkDetailIntent = new Intent(getActivity(), DrinkDetailActivity.class);
        drinkDetailIntent.putExtra("PROFILE_ID",profileID);
        drinkDetailIntent.putExtra("DRINK_ID",currentDrinkID);
        startActivity(drinkDetailIntent);
        getActivity().finish();


    }
}
