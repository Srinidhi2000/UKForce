package com.example.android.ukforce;

import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.content.Context;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.ukforce.favdata.favContract;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

// TO DISPLAY THE DETAILS OF A SPECIFIC CRIME WHEN CLICKED
public class crimeSpecific extends AppCompatActivity   {
    ArrayList<String> idtocheck;
    crime selected,fromquery;
    int cnt=0;
    private Uri currenturi=null;
    ProgressBar loading;
    TextView category,lat,longitude,crimename,id,month,force,crimecontext,sub,emptystate;
    private static final int OUTCOME_LOADER_ID=1;
    private static final int CURSOR_LOADER_ID=2;
    String checkclass="3";
    TextView l1,l2,l3,l4,star;
    boolean isid=false;
    LinearLayout layout;
public static final String SHARED_PREFS="sharedPrefs";
public static  final String CRIMEID="crimeId";
  private  String OUTCOME_URL="https://data.police.uk/api/outcomes-for-crime/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.specificcrime_activity);
        star = findViewById(R.id.fav);
        layout = findViewById(R.id.layout);
        l1 = findViewById(R.id.l1);
        l2 = findViewById(R.id.l2);
        l3 = findViewById(R.id.location);
        l4 = findViewById(R.id.crimeidtext);
         idtocheck=new ArrayList<>();

        category = findViewById(R.id.category);
        lat = findViewById(R.id.crime_latitude);
        longitude = findViewById(R.id.crime_longitude);
        crimename = findViewById(R.id.crime_name);
        id = findViewById(R.id.ID);
        month = findViewById(R.id.month);
        force = findViewById(R.id.operation);
        crimecontext = findViewById(R.id.crime_context);
        sub = findViewById(R.id.location_subtype);
        loading = findViewById(R.id.loading);
        emptystate = findViewById(R.id.empty);
        l1.setVisibility(View.INVISIBLE);
        l2.setVisibility(View.INVISIBLE);
        l3.setVisibility(View.INVISIBLE);
        l4.setVisibility(View.INVISIBLE);
        star.setVisibility(View.INVISIBLE);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo active = connectivityManager.getActiveNetworkInfo();
        if (active != null && active.isConnected()) {
            loadData();
            String t=(String) getTitle();
            Intent intent = getIntent();
            checkclass = intent.getStringExtra("fav");
            if (checkclass.equals("1")) {
                setTitle(getString(R.string.app_name));
                selected = EnterCrimedetails.current;
                check();
            } else {
                setTitle(getString(R.string.fav_title));
                currenturi = intent.getData();
                if (currenturi != null)
                    getLoaderManager().initLoader(CURSOR_LOADER_ID, null, cursorlistener);
                }


        }
        else {
            loading.setVisibility(View.GONE);
            emptystate.setText("No internet connection.");
        }

        }

private void check()
{String checkid=selected.getCrime_id();
    if(checkid!=null)
    {OUTCOME_URL=OUTCOME_URL+checkid;
        isid=true;
    }

    if(isid) {
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(OUTCOME_LOADER_ID, null, outcomeslistener);

    }
    else{displaybasicdetails();}


}


private void displaybasicdetails()
{  loading.setVisibility(View.GONE);
    l1.setVisibility(View.VISIBLE);
    l2.setVisibility(View.VISIBLE);
    l3.setVisibility(View.VISIBLE);
    l4.setVisibility(View.VISIBLE);
    star.setVisibility(View.VISIBLE);
    category.setText(selected.getCategory());
    lat.setText(selected.getLatitude());
    longitude.setText(selected.getLongitude());
    crimename.setText(selected.getName());
    id.setText(selected.getCrime_id());
    month.setText(selected.getMonth());
    force.setText("Operation by "+selected.getLocation_type());
    {crimecontext.setText(selected.getCrime_context());}
    if(selected.getLocation_subtype().equals(""))
        sub.setVisibility(View.GONE);
    else
    sub.setText(selected.getLocation_subtype());
emptystate.setVisibility(View.GONE);
}

public void selectstar(View view)
{
    String tit=(String) getTitle();
    if(tit.equals("UKForce")) {
          if(idtocheck.size()!=0)
          { for(int i=0;i<idtocheck.size();i++)
        {  if(idtocheck.get(i).equals(selected.getCrime_id())){cnt++;
        Toast.makeText(this,"Already added to favorites",Toast.LENGTH_SHORT).show();
        }}}
        if(cnt==0)
        {Toast.makeText(this,"Added to favourites",Toast.LENGTH_SHORT).show();
        ContentValues values=new ContentValues();
        values.put(favContract.favEntry.c1streetname,selected.getName());
        values.put(favContract.favEntry.c2category,selected.getCategory());
        values.put(favContract.favEntry.c3month,selected.getMonth());
        values.put(favContract.favEntry.c4id,selected.getCrime_id());
        values.put(favContract.favEntry.c5latitude,selected.getLatitude());
        values.put(favContract.favEntry.c6longitude,selected.getLongitude());
        values.put(favContract.favEntry.c7location,selected.getLocation_type());
        values.put(favContract.favEntry.c8context,selected.getCrime_context());
        values.put(favContract.favEntry.c9subtype,selected.getLocation_subtype());
        values.put(favContract.favEntry.c10outcome,selected.getOutcomestatus());

        if(currenturi==null)
        {Uri newuri=getContentResolver().insert(favContract.favEntry.CONTENT_URI,values);
            if(newuri==null)
            {Toast.makeText(this,"Insertion Failed",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Insertion successful",Toast.LENGTH_SHORT).show();
            }
        }
        idtocheck.add(selected.getCrime_id());
        saveData();
        }}
            else
            {   Toast.makeText(this,"Removed from favourites",Toast.LENGTH_SHORT).show();
                //delete option
                if(currenturi!=null)
                {
                    int rowsdeleted=getContentResolver().delete(currenturi,null,null);
                    if(rowsdeleted==0)
                    {
                        Toast.makeText(this,"Deletion failed",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {     for(int i=0;i<idtocheck.size();i++)
                          {if(idtocheck.get(i).equals(selected.getCrime_id()))
                          {idtocheck.remove(i);}}
                        saveData();
                        Toast.makeText(this,"Successfully deleted",Toast.LENGTH_SHORT).show();
                    }
                }
           finish();
            }
}

    private LoaderCallbacks<arrayoutcomes> outcomeslistener=new LoaderCallbacks<arrayoutcomes>() {
        @Override
        public Loader<arrayoutcomes> onCreateLoader(int id, Bundle args) {
            return new crimeoutcomeloader(getApplicationContext(),OUTCOME_URL);
        }

        @Override
        public void onLoadFinished(Loader<arrayoutcomes> loader, arrayoutcomes data) {
            loading.setVisibility(View.GONE);
            emptystate.setText("No info found");
            if(data!=null)
            {
                for(int i=0;i<data.getCrimeoutcomes().size();i++)
                { String personid=data.getCrimeoutcomes().get(i).getPerson_id();
                    if (!personid.equals("null"))
                    {TextView cid=new TextView(getApplicationContext());
                        cid.setText(personid);
                        layout.addView(cid);
                        cid.setPadding(8,0,0,30);
                        cid.setTextColor(Color.parseColor("#B4BAC0"));}
                    String codename=data.getCrimeoutcomes().get(i).getCodename();
                    TextView code=new TextView(getApplicationContext());
                    code.setText(codename);
                    String categorydate=data.getCrimeoutcomes().get(i).getCategorydate();
                    TextView date=new TextView(getApplicationContext());
                    date.setText(categorydate);
                    layout.addView(date);
                    layout.addView(code);
                    date.setPadding(8,0,0,8);
                    code.setPadding(8,0,0,8);
                }

            }
            displaybasicdetails();


        }

        @Override
        public void onLoaderReset(Loader<arrayoutcomes> loader) {

        }
    };

    private LoaderCallbacks<Cursor> cursorlistener=new LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            String[] projection={
                   favContract.favEntry.rowid,
                favContract.favEntry.c1streetname,
                favContract.favEntry.c2category,
                favContract.favEntry.c3month,
                favContract.favEntry.c4id,
                favContract.favEntry.c5latitude,
                favContract.favEntry.c6longitude,
                favContract.favEntry.c7location,
                favContract.favEntry.c8context,
                favContract.favEntry.c9subtype,
                favContract.favEntry.c10outcome};

            return new CursorLoader(getApplicationContext(),currenturi,projection,null,null,null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
           if(cursor==null||cursor.getCount()<1)
           {
               return;
           }
              if(cursor.moveToFirst())
              {
                  int c1=cursor.getColumnIndex(favContract.favEntry.c1streetname);
                  int c2=cursor.getColumnIndex(favContract.favEntry.c2category);
                  int c3=cursor.getColumnIndex(favContract.favEntry.c3month);
                  int c4=cursor.getColumnIndex(favContract.favEntry.c4id);
                  int c5=cursor.getColumnIndex(favContract.favEntry.c5latitude);
                  int c6=cursor.getColumnIndex(favContract.favEntry.c6longitude);
                  int c7=cursor.getColumnIndex(favContract.favEntry.c7location);
                  int c8=cursor.getColumnIndex(favContract.favEntry.c8context);
                  int c9=cursor.getColumnIndex(favContract.favEntry.c9subtype);
                  int c10=cursor.getColumnIndex(favContract.favEntry.c10outcome);
                  String s1=cursor.getString(c1);
                  String s2=cursor.getString(c2);
                  String s3=cursor.getString(c3);
                  String s4=cursor.getString(c4);
                  String s5=cursor.getString(c5);
                  String s6=cursor.getString(c6);
                  String s7=cursor.getString(c7);
                  String s8=cursor.getString(c8);
                  String s9=cursor.getString(c9);
                  String s10=cursor.getString(c10);
                  fromquery=new crime(s1,s3,s2,s4,s5,s6,s7,s8,s9,s10);
                  selected = fromquery;
              check();
              }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };
public void saveData()
{ SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(crimeSpecific.this);
    SharedPreferences.Editor editor=sharedPreferences.edit();
    Gson gson=new Gson();
    String json=gson.toJson(idtocheck);
    editor.putString(CRIMEID,json);
    editor.apply();
}
public void loadData()
{ SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(crimeSpecific.this);
Gson gson=new Gson();
String json=sharedPreferences.getString(CRIMEID,null);
    Type type=new TypeToken<ArrayList<String>>(){}.getType();
    idtocheck=gson.fromJson(json,type);
    if(idtocheck==null){
        idtocheck=new ArrayList<>();
    }
}

}
