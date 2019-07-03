package com.example.android.ukforce;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.ukforce.favdata.favContract;
// SET THE TEXTVIEWS FOR LIST OF CRIMES IN FAVOURITES
public class favCursorAdapter extends CursorAdapter {

    public favCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.crime_list_item,parent,false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView street_name=(TextView)view.findViewById(R.id.crimename);
        TextView category=(TextView)view.findViewById(R.id.crimecategory);
        TextView month=(TextView)view.findViewById(R.id.crimemonth);
        int nameIndex=cursor.getColumnIndex(favContract.favEntry.c1streetname);
        int categoryindex=cursor.getColumnIndex(favContract.favEntry.c2category);
        int date=cursor.getColumnIndex(favContract.favEntry.c3month);
         String streetname=cursor.getString(nameIndex);
         String crimecategory=cursor.getString(categoryindex);
         String crimemonth=cursor.getString(date);
street_name.setText(streetname);
category.setText(crimecategory);
month.setText(crimemonth);
    }
}
