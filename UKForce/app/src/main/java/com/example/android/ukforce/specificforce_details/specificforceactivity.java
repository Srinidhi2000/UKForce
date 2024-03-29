package com.example.android.ukforce.specificforce_details;

import android.content.Context;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.ukforce.R;
// TO DISPLAY A SPECIFIC FORCE'S DETAILS
public class specificforceactivity extends AppCompatActivity implements LoaderCallbacks<Specificforce> {
    private static  String SPECIFIC_URL="null";
    ProgressBar loading;
    String tosearch;
    ListView contact_listview;
    LinearLayout contactslayout;
    TextView emptystate,title,description,url,phone;
    private static final int FORCE_LOADER_ID=1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.specific_force_activity);
        emptystate=(TextView)findViewById(R.id.empty);
        loading=(ProgressBar)findViewById(R.id.loading);
        contactslayout=(LinearLayout)findViewById(R.id.contacts_layout);
        title=(TextView)findViewById(R.id.title);
        description=(TextView)findViewById(R.id.description);
        description.setVisibility(View.GONE);
        url=(TextView)findViewById(R.id.url);
        phone=(TextView)findViewById(R.id.phone);
        Intent intent=getIntent();
        SPECIFIC_URL=intent.getStringExtra("specificurl");
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo active=connectivityManager.getActiveNetworkInfo();
        if(active!=null&&active.isConnected())
        {
            LoaderManager loaderManager=getLoaderManager();
            loaderManager.initLoader(FORCE_LOADER_ID,null,this);

        }else{
           loading.setVisibility(View.GONE);
            emptystate.setText("No internet connection.");
        }
    }

    @Override
    public Loader<Specificforce> onCreateLoader(int id, Bundle args) {
        return new specificforceloader(this,SPECIFIC_URL);
    }

    @Override
    public void onLoadFinished(Loader<Specificforce> loader, Specificforce data) {

        loading.setVisibility(View.GONE);
        emptystate.setText("No info found");
        if(data!=null)
        { title.setText(data.getName());
            if(data.getDescription()!="null") {
              description.setVisibility(View.VISIBLE);
              String newstring=removeextras(data.getDescription());
                description.setText(newstring);
            }phone.setText(data.getPhone());
             url.setText(data.getUrl());
             for(int i=0;i<(data.getContact().size());i++) {
                  final TextView urls = new TextView(this);
                 urls.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         tosearch=(String) urls.getText();
                         Uri current=Uri.parse(tosearch);
                         Intent intent=new Intent(Intent.ACTION_VIEW,current);
                         startActivity(intent);
                     }
                 });
                 urls.setText(data.getContact().get(i).getContacturl());
                 TextView des = new TextView(this);
                 if(data.getContact().get(i).getContactdes()!="null") {
                 String newdes = removeextras(data.getContact().get(i).getContactdes());
                 des.setText(newdes);
             }
             else{des.setVisibility(View.GONE);}
             TextView title=new TextView(this);
                title.setText(data.getContact().get(i).getContacttitle());
                contactslayout.addView(title);
                contactslayout.addView(des);
                contactslayout.addView(urls);
                url.setPadding(8,0,0,30);
                des.setPadding(8,0,0,8);
                title.setPadding(8,0,0,8);
                title.setTextColor(Color.BLACK);
                urls.setTextColor(Color.parseColor("#01579b"));

            }

              emptystate.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Specificforce> loader) {
    }

    private String removeextras(String s)
    {   String r1="<p>";
        String r2="</p>";
        String n1=s.replace(r1,"");
        String newstring=n1.replace(r2,"");
        return newstring;

    }
    public void formoreinfo(View view)
    { tosearch=(String) url.getText();
        Uri current=Uri.parse(tosearch);
        Intent intent=new Intent(Intent.ACTION_VIEW,current);
        startActivity(intent);

    }

}
