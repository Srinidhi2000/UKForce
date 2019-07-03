package com.example.android.ukforce.favdata;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class favContract {
    private favContract() {}
    public static final String CONTENT_AUTHORITY="com.example.android.ukforce";
    public static final Uri BASE_CONTENT_URI=Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH="crime";
    public static class favEntry implements BaseColumns {

       public static final Uri CONTENT_URI=Uri.withAppendedPath(BASE_CONTENT_URI,PATH);
       public static final String CONTENT_LIST=ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH;
       public static final String CONTENT_ITEM=ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH;
        public static final String rowid=BaseColumns._ID;
       public  static final String TABLE_NAME="Showfav_data";
        public static final String c1streetname="STREET_NAME";
        public static final String c2category="CATEGORY";
        public static final String c3month="MONTH";
        public static final String c4id="CRIME_ID";
        public static final String c5latitude="LATITUDE";
        public static final String c6longitude="LONGITUDE";
        public static final String c7location="LOCATION_TYPE";
        public static final String c8context="CONTEXT";
        public static final String c9subtype="SUB_TYPE";
        public static final String c10outcome="OUTCOME";
    }
}
