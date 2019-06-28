package com.example.android.ukforce;

import android.content.Context;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class forceactivity extends AppCompatActivity implements LoaderCallbacks<List<Force>> {
     private TextView Emptystate;
     private forceadapter adapter;
     public static String forcename;
     ProgressBar loading;
     private static final int FORCE_LOADER_ID=1;
     public final static String BASE_URL="https://data.police.uk/api/forces";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Emptystate=(TextView)findViewById(R.id.empty);
        loading=(ProgressBar)findViewById(R.id.loading);
        ListView forcelistview=(ListView)findViewById(R.id.list);
        forcelistview.setEmptyView(Emptystate);
        adapter =new forceadapter(this,new ArrayList<Force>());
           forcelistview.setAdapter(adapter);
           forcelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   Force current=adapter.getItem(position);
                 String name=current.getforcename();
                 forcename=BASE_URL+"/"+name;
                   Intent intent=new Intent(forceactivity.this,specificforceactivity.class);
                  intent.putExtra("specificurl",forcename);
                   startActivity(intent);

               }
           });
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo active=connectivityManager.getActiveNetworkInfo();
        if(active!=null&&active.isConnected())
        {
            LoaderManager loaderManager=getLoaderManager();
            loaderManager.initLoader(FORCE_LOADER_ID,null,this);

        }else{
            loading.setVisibility(View.GONE);
            Emptystate.setText("No internet connection.");
        }

    }

    @Override
    public Loader<List<Force>> onCreateLoader(int id, Bundle args) {
          return new forceloader(this,BASE_URL);

    }

    @Override
    public void onLoadFinished(Loader<List<Force>> loader, List<Force> forces) {
loading.setVisibility(View.GONE);
adapter.clear();
Emptystate.setText("No earthquakes found");
if(forces!=null&&!forces.isEmpty())
{     adapter.addAll(forces);
    Emptystate.setVisibility(View.GONE);
}
    }

    @Override
    public void onLoaderReset(Loader<List<Force>> loader) {
adapter.clear();
    }
}
