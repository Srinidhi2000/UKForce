package com.example.android.ukforce.favdata;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

public class favprovider extends ContentProvider {
    private display_fav dbhelper;
private static final int CRIME=1;
private static final int CRIME_SINGLE=2;
private static final UriMatcher urimatcher=new UriMatcher(UriMatcher.NO_MATCH);
static{
    urimatcher.addURI(favContract.CONTENT_AUTHORITY,favContract.PATH,CRIME);
    urimatcher.addURI(favContract.CONTENT_AUTHORITY,favContract.PATH+"/#",CRIME_SINGLE);
}
    @Override
    public boolean onCreate() {
        dbhelper=new display_fav(getContext());
        return true;
    }


    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database=dbhelper.getReadableDatabase();
     Cursor cursor;
     int match=urimatcher.match(uri);
     switch(match)
     {
         case CRIME:cursor=database.query(favContract.favEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
             break;
         case CRIME_SINGLE:
             selection=favContract.favEntry.rowid+"=?";
             selectionArgs=new String[]{String.valueOf(ContentUris.parseId(uri))};
             cursor=database.query(favContract.favEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
             break;
         default:throw new IllegalArgumentException("Cannot query"+uri);
     }
     cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;

    }

    @Override
    public String getType(@NonNull Uri uri) {
    final int match=urimatcher.match(uri);
    switch(match)
    {
        case CRIME:
            return favContract.favEntry.CONTENT_LIST;
        case CRIME_SINGLE:
            return favContract.favEntry.CONTENT_ITEM;
            default:
                throw new IllegalStateException("Unknown URI");
    }
    }

    @Override
    public Uri insert(Uri uri,  ContentValues values) {
        SQLiteDatabase database=dbhelper.getWritableDatabase();
        long id=database.insert(favContract.favEntry.TABLE_NAME,null,values);
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri,id);
    }

    @Override
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {
        SQLiteDatabase database=dbhelper.getWritableDatabase();
        selection=favContract.favEntry.rowid+"=?";
        selectionArgs=new String[]{String.valueOf(ContentUris.parseId(uri))};
        int rows=database.delete(favContract.favEntry.TABLE_NAME,selection,selectionArgs);
        if(rows!=0)
        {
            getContext().getContentResolver().notifyChange(uri,null);
        }

        return rows;
    }

    @Override
    public int update( Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {
        return 0;
    }
}

