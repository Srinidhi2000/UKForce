
package com.example.android.ukforce.favdata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class display_fav extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="Showfav.db";
    public static final int DATABASE_VERSION=1;

    public display_fav(Context context){
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable="CREATE TABLE " + favContract.favEntry.TABLE_NAME + " ("
                + favContract.favEntry.rowid + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + favContract.favEntry.c1streetname + " TEXT, "
                + favContract.favEntry.c2category + " TEXT, "
                + favContract.favEntry.c3month + " TEXT, "
                + favContract.favEntry.c4id + " TEXT, "
                + favContract.favEntry.c5latitude + " TEXT, "
                + favContract.favEntry.c6longitude + " TEXT, "
                + favContract.favEntry.c7location + " TEXT, "
                +favContract.favEntry.c8context + " TEXT, "
                +favContract.favEntry.c9subtype + " TEXT, "
                +favContract.favEntry.c10outcome + " TEXT);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
db.execSQL("DROP TABLE IF EXISTS " + favContract.favEntry.TABLE_NAME);
    }
}
