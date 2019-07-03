package com.example.android.ukforce;

public class crimeoutcome {
String codename,categorydate,person_id;

    public crimeoutcome(String codename, String categorydate, String person_id) {
        this.codename = codename;
        this.categorydate = categorydate;
        this.person_id = person_id;
    }

    public String getCodename() {
        return codename;
    }

    public String getCategorydate() {
        return categorydate;
    }

    public String getPerson_id() {
        return person_id;
    }
}
