package com.example.android.ukforce;

import java.util.ArrayList;

public class arrayoutcomes {
    private ArrayList<crimeoutcome> outcomes;

    public arrayoutcomes(ArrayList<crimeoutcome> crimeoutcomes) {
        this.outcomes = crimeoutcomes;
    }

    public ArrayList<crimeoutcome> getCrimeoutcomes() {
        return outcomes;
    }
}
