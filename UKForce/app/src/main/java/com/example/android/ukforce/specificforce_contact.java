package com.example.android.ukforce;

public class specificforce_contact {
    private String contacturl;
    private String contactdes;
    private String contacttitle;

    public specificforce_contact(String contacturl, String contactdes, String contacttitle) {
        this.contacturl = contacturl;
        this.contactdes = contactdes;
        this.contacttitle = contacttitle;
    }

    public String getContacturl() {
        return contacturl;
    }

    public String getContactdes() {
        return contactdes;
    }

    public String getContacttitle() {
        return contacttitle;
    }
}
