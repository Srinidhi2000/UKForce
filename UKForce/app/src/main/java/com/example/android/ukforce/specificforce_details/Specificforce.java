package com.example.android.ukforce.specificforce_details;

import java.util.ArrayList;

public class Specificforce {
private String name;
private String phone;
private String url;
private String description;
private ArrayList<specificforce_contact> contact;

    public Specificforce(String name,  String description,String phone, String url,ArrayList<specificforce_contact> contact) {
        this.name = name;
        this.phone = phone;
        this.url = url;
        this.description = description;
        this.contact=contact;
    }

    public ArrayList<specificforce_contact> getContact() {
        return contact;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }
}
