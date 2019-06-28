package com.example.android.ukforce;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;



public class specificforceloader extends AsyncTaskLoader<Specificforce> {
    private String Url;
    public specificforceloader( Context context,String givenurl) {
        super(context);
        Url=givenurl;
    }

    @Override
    protected void onStartLoading() {forceLoad();
    }

    @Nullable
    @Override
    public Specificforce loadInBackground() {
        if(Url==null)
            return null;
        Specificforce forces=Connect.fetchspecificdata(Url);
        return forces;
    }
}
