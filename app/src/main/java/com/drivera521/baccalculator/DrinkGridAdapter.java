//Daniel Rivera
//MDV 469 - 1805
//DrinkGridAdapter.java

package com.example.daniel.riveradaniel_project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daniel.riveradaniel_project1.drink_database.DrinkData;

import java.util.ArrayList;

public class DrinkGridAdapter extends BaseAdapter {

    private ArrayList<DrinkData> drinkListing = new ArrayList<>();
    private final Context context;
    private final LayoutInflater infltr;
    private final int preference;

    public DrinkGridAdapter (Context context, ArrayList<DrinkData> drinks, int preference){
        drinkListing = drinks;
        this.context = context;
        this.preference = preference;

        infltr = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

    @Override
    public int getCount() {
        return drinkListing.size();
    }

    @Override
    public Object getItem(int position) {
        return drinkListing.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        String drinkTitle = drinkListing.get(position).getDrinkName();
        String imageLocation = drinkListing.get(position).getDrinkImage();

        int id = context.getResources().getIdentifier(imageLocation,null,null);


        if (preference == 0) {
            View rowView = infltr.inflate(R.layout.drink_list_grid_layout,null);

            TextView drinkName = rowView.findViewById(R.id.drink_grid_view_text);
            ImageView drinkImage = rowView.findViewById(R.id.drink_grid_view_image);

            drinkName.setText(drinkTitle);
            if(position >=8){
                Bitmap imageBMP = BitmapFactory.decodeFile(imageLocation);
                drinkImage.setImageBitmap(imageBMP);
            }else {
                drinkImage.setImageResource(id);
            }
            return rowView;
        } else {
            View rowView = infltr.inflate(R.layout.drink_list_layout, null);

            TextView drinkName = rowView.findViewById(R.id.drink_list_view_row_text);
            ImageView drinkImage = rowView.findViewById(R.id.drink_list_view_image);

            drinkName.setText(drinkTitle);
            if(position >= 8){
                Bitmap imageBMP = BitmapFactory.decodeFile(imageLocation);
                drinkImage.setImageBitmap(imageBMP);
            }else {
                drinkImage.setImageResource(id);
            }

            return rowView;
        }
    }
}
