package com.example.android.ukforce;

public class crime {
    String name,month,category,crime_id;
    String latitude,longitude,location_type,crime_context;
    String location_subtype,outcomestatus;

    public crime(String name, String month, String category, String crime_id, String latitude, String longitude, String location_type,
                 String crime_context, String location_subtype, String outcomestatus) {
        this.name = name;
        this.month = month;
        this.category = category;
        this.crime_id = crime_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location_type = location_type;
        this.crime_context= crime_context;
        this.location_subtype = location_subtype;
        this.outcomestatus = outcomestatus;
    }

    public String getName() {
        return name;
    }

    public String getMonth() {
        return month;
    }

    public String getCategory() {
        return category;
    }

    public String getCrime_id() {
        return crime_id;
    }
    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLocation_type() {
        return location_type;
    }

    public String getCrime_context() {
        return crime_context;
    }

    public String getLocation_subtype() {
        return location_subtype;
    }

    public String getOutcomestatus() {
        return outcomestatus;
    }
}
