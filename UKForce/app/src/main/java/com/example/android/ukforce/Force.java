package com.example.android.ukforce;

public class Force {
    private String name;
    private String id;
    public Force(String forceid,String forcename)
    { name=forcename;
      id=forceid;
    }
public String getforcename()
{return name;}
public String getforceid()
{return id;}
}
