package com.example.android.ukforce;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class crimeadapter extends ArrayAdapter<crime> {
    int cnt=0;

    public crimeadapter(Context context, List<crime> crimes) {
        super(context,0,crimes); }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        View listitemView=convertView;
        if(listitemView==null)
        {listitemView=LayoutInflater.from(getContext()).inflate(R.layout.crime_list_item,parent,false);
        }

        final crime current=getItem(position);
        TextView crime_name=(TextView)listitemView.findViewById(R.id.crimename);
        TextView crime_category=(TextView)listitemView.findViewById(R.id.crimecategory);
        TextView crime_month=(TextView)listitemView.findViewById(R.id.crimemonth);
       crime_name.setText(current.getName());
     crime_category.setText(current.getCategory());
     crime_month.setText(current.getMonth());
        return listitemView;
    }
}
