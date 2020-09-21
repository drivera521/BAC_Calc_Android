package com.drivera521.baccalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.drivera521.baccalculator.drink_database.DrinkLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class BACListAdapter extends BaseAdapter {

    private ArrayList<DrinkLog> drinkingLog = new ArrayList<>();
     private final LayoutInflater inflatr;
    public BACListAdapter(Context context, ArrayList<DrinkLog> drinkingLogs){

        drinkingLog = drinkingLogs;
        inflatr = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return drinkingLog.size();
    }

    @Override
    public Object getItem(int position) {
        return drinkingLog.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewRow = inflatr.inflate(R.layout.bac_list_layout,null);

        TextView drinkListName = viewRow.findViewById(R.id.bac_list_drink_name_item);
        TextView drinkListTime = viewRow.findViewById(R.id.bac_list_time);
        drinkListName.setText(drinkingLog.get(position).getDrinkName());
        long drinkTime = Long.valueOf(drinkingLog.get(position).getConsumedTime());

        drinkListTime.setText(getDateString(drinkTime));

        return viewRow;

    }

    private String getDateString(long drinkTime){

        String format = "MM/dd/yyyy hh:mm:ss a";

        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(drinkTime);

        return sdf.format(calendar.getTime());

    }
}
