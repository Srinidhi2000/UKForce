package com.example.android.ukforce;

import android.app.LoaderManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.ukforce.favdata.display_fav;

import java.util.ArrayList;
import java.util.List;
// TO GET LATITUDE,LONGITUDE AND MONTH FROM USER AND DISPLAY THE CRIME LIST
public class EnterCrimedetails extends AppCompatActivity implements LoaderCallbacks<List<crime>> {
    EditText latitude,longitude,month;
    Button showcrime,showfav;
    Boolean isconnect=false;Boolean ishide=false;Boolean isdatEentered;
    String lat,lon,mon;
    ListView listView;
    ProgressBar loading;
    crimeadapter adapter;
    LinearLayout lon_layout,mon_layout;
    public static crime current;
    int cnt=0;
    TextView emptystate,monthtext,hide,lattext;
    private static final int CRIME_LOADER_ID = 1;
    public final static String BASE_URL = "https://data.police.uk/api/crimes-street/all-crime";
    LoaderManager loaderManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entercrimedetails);
         latitude=findViewById(R.id.latitude);
         showfav=findViewById(R.id.button_fav);
         lon_layout=findViewById(R.id.l_layout);
         mon_layout=findViewById(R.id.month_layout);
         longitude=findViewById(R.id.longitude);
         month=findViewById(R.id.month);
         hide=findViewById(R.id.hide);
         lattext=findViewById(R.id.lattext);
         monthtext=findViewById(R.id.monthtext);
         listView=findViewById(R.id.list);
         loading=findViewById(R.id.loading);
         loading.setVisibility(View.GONE);
         emptystate=findViewById(R.id.empty);
         emptystate.setVisibility(View.GONE);
        showcrime=findViewById(R.id.button_showcrime);
        adapter=new crimeadapter(this,new ArrayList<crime>());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                current=adapter.getItem(position);
                Intent intent = new Intent(EnterCrimedetails.this, crimeSpecific.class);
                intent.putExtra("fav","1");
                startActivity(intent);
            }
        });


    }

        public void showcrimelist(View view)
        { lat=latitude.getText().toString();
        if(TextUtils.isEmpty(lat))
        { latitude.setError("Enter the latitude");
         latitude.requestFocus();
         return;
        }
        lon=longitude.getText().toString();
        if(TextUtils.isEmpty(lon))
        {
            longitude.setError("Enter the longitude");
            longitude.requestFocus();
            return;
        }
        mon=month.getText().toString();

        isdatEentered=true;
        if(mon.equals("") ||TextUtils.isEmpty(mon))
        {
          isdatEentered=false;

        }
          loading.setVisibility(View.VISIBLE);
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo active = connectivityManager.getActiveNetworkInfo();
            if (active != null && active.isConnected()) {
                isconnect=true;
            } else {

                emptystate.setVisibility(View.VISIBLE);
                emptystate.setText("No internet connection.");
                loading.setVisibility(View.GONE);
            }
        if(isconnect && cnt == 0) {
            loaderManager = getLoaderManager();
            loaderManager.initLoader(CRIME_LOADER_ID, null, this);
            cnt++;
            }
       else if(isconnect&&cnt!=0)
        {
            loaderManager.restartLoader(CRIME_LOADER_ID,null,this);
        }
        }

public void hide(View view)
{ applychanges(ishide);
 ishide=!ishide;
}
private void applychanges(boolean b)
{if (b)
{latitude.setVisibility(View.VISIBLE);
lattext.setVisibility(View.VISIBLE);
lon_layout.setVisibility(View.VISIBLE);
mon_layout.setVisibility(View.VISIBLE);
showcrime.setVisibility(View.VISIBLE);
hide.setText("Hide box");
}
else
{latitude.setVisibility(View.GONE);
    lattext.setVisibility(View.GONE);
    lon_layout.setVisibility(View.GONE);
    mon_layout.setVisibility(View.GONE);
    showcrime.setVisibility(View.GONE);
    hide.setText("Show box");
}
}

    @Override
    public Loader<List<crime>> onCreateLoader(int id, Bundle args) {
        Uri baseuri=Uri.parse(BASE_URL);
        Uri.Builder uribuilder=baseuri.buildUpon();
        uribuilder.appendQueryParameter("lat",lat);
        uribuilder.appendQueryParameter("lng",lon);
        if(isdatEentered)
        {uribuilder.appendQueryParameter("date",mon);}
        return new crimeloader(this,uribuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<crime>> loader, List<crime> data) {
        loading.setVisibility(View.GONE);
        adapter.clear();
        emptystate.setVisibility(View.VISIBLE);
        emptystate.setText("No Crime Details Found");
        if (data != null && !data.isEmpty()) {
            adapter.addAll(data);
            emptystate.setVisibility(View.GONE);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<crime>> loader) {
adapter.clear();
    }

public void showfavourites(View view)
{
Intent intent=new Intent(EnterCrimedetails.this,viewfavourites.class);
startActivity(intent);
}



}
