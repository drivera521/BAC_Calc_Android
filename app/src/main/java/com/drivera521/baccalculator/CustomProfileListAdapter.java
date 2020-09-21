package com.drivera521.baccalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomProfileListAdapter extends BaseAdapter {

    private final ArrayList<String> profileNames;

    private final LayoutInflater inflatr;

    public CustomProfileListAdapter(Context context, ArrayList<String> profiles){

        Context mContext = context;

        profileNames = profiles;

        inflatr = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return profileNames.size();
    }

    @Override
    public Object getItem(int position) {
        return profileNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = inflatr.inflate(R.layout.profile_list_adapter_layout,parent,false);

        TextView profileName = rowView.findViewById(R.id.profile_select_list_view_item);
        profileName.setText(profileNames.get(position));

        return rowView;
    }
}
