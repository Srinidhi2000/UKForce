package com.example.android.ukforce.force_details;

import android.content.Context;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.android.ukforce.EnterCrimedetails;
import com.example.android.ukforce.R;
import com.example.android.ukforce.specificforce_details.specificforceactivity;

import java.util.ArrayList;
import java.util.List;
//TO DISPLAY THE LIST OF FORCES IN UK
public class forceactivity extends AppCompatActivity implements LoaderCallbacks<List<Force>> {
    private TextView Emptystate;
    private forceadapter adapter;
    private String forcename;
    ArrayList<Force> l1,l2;
    ProgressBar loading;
    ListView forcelistview;
    private static final int FORCE_LOADER_ID = 1;
    public final static String BASE_URL = "https://data.police.uk/api/forces";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Emptystate = findViewById(R.id.empty);
        loading = findViewById(R.id.loading);
         forcelistview = findViewById(R.id.list);
        forcelistview.setEmptyView(Emptystate);

        adapter = new forceadapter(this, new ArrayList<Force>());
        forcelistview.setAdapter(adapter);
        forcelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Force current = adapter.getItem(position);
                String name = current.getforceid();
                forcename = BASE_URL + "/" + name;
                Intent intent = new Intent(forceactivity.this, specificforceactivity.class);
                intent.putExtra("specificurl", forcename);
                startActivity(intent);

            }
        });
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo active = connectivityManager.getActiveNetworkInfo();
        if (active != null && active.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(FORCE_LOADER_ID, null, this);

        } else {
            loading.setVisibility(View.GONE);
            Emptystate.setText("No internet connection.");
        }
    }

    @Override
    public Loader<List<Force>> onCreateLoader(int id, Bundle args) {
        return new forceloader(this, BASE_URL);

    }

    @Override
    public void onLoadFinished(Loader<List<Force>> loader, List<Force> forces) {
        loading.setVisibility(View.GONE);
        adapter.clear();
        l1=new ArrayList<>();
        l2=new ArrayList<>();

        Emptystate.setText("No Forces Found");
        if (forces != null && !forces.isEmpty()) {
            l1.addAll(forces);
            adapter.addAll(forces);
            Emptystate.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Force>> loader) {
        adapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
getMenuInflater().inflate(R.menu.main,menu);
MenuItem search=menu.findItem(R.id.action_search);
SearchView searchview=(SearchView)search.getActionView();
searchview.setFocusable(false);
searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
    @Override
    public boolean onQueryTextSubmit(String query) {
    return false;

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        l2.clear();
        String word=newText.toUpperCase();
        for(int i=0;i<l1.size();i++)
        {
            if(l1.get(i).getforcename().toUpperCase().contains(word))
            {
                l2.add(l1.get(i));
                Emptystate.setVisibility(View.GONE);
            }
        }    adapter.clear();
        adapter.addAll(l2);
        forcelistview.setAdapter(adapter);
        return false;
    }
});

return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.viewcrime)
        {  Intent intent=new Intent(this,EnterCrimedetails.class);
           startActivity(intent);
           return true;
           }
        return super.onOptionsItemSelected(item);
    }
}