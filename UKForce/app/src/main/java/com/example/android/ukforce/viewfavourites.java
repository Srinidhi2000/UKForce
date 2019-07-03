package com.example.android.ukforce;

import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;

import com.example.android.ukforce.favdata.display_fav;
import com.example.android.ukforce.favdata.favContract;

import java.util.ArrayList;
// TO DISPLAY THE CRIMES WHICH WERE ADDED TO FAVOURITES
public class viewfavourites extends AppCompatActivity implements LoaderCallbacks<Cursor> {
    private static final int SPECIFIC_LOADER=1;
    favCursorAdapter adapter;
    ListView listView;
    TextView emptystate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showfavourites);
        listView=(ListView) findViewById(R.id.list);
        emptystate=(TextView)findViewById(R.id.emptystate);
        listView.setEmptyView(emptystate);
        adapter=new favCursorAdapter(this,null);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(viewfavourites.this,crimeSpecific.class);
                intent.putExtra("fav","2");
                Uri currenturi=ContentUris.withAppendedId(favContract.favEntry.CONTENT_URI,id);
                intent.setData(currenturi);
                startActivity(intent);
            }
        });

    getLoaderManager().initLoader(SPECIFIC_LOADER,null,this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection={
        favContract.favEntry.rowid,
        favContract.favEntry.c1streetname,
        favContract.favEntry.c2category,
        favContract.favEntry.c3month
   /*     favContract.favEntry.c4id,
        favContract.favEntry.c5latitude,
        favContract.favEntry.c6longitude,
        favContract.favEntry.c7location,
        favContract.favEntry.c8context,
        favContract.favEntry.c9subtype,
        favContract.favEntry.c10outcome
        */};
        return new CursorLoader(this,favContract.favEntry.CONTENT_URI,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
adapter.swapCursor(null);
    }
}
