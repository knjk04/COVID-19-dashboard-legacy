package com.karankumar.covid19dashboard.backend.domain;

import com.karankumar.covid19dashboard.backend.utils.Util;

public class CountryTotal {
    // From JSON
    private int numberOfCases;
    private String date;

    public CountryTotal(int cases, String date) {
        this.numberOfCases = cases;
        this.date = Util.formatDate(date);
    }

    public int getNumberOfCases() {
        return numberOfCases;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "CountryLive{" +
                "cases=" + numberOfCases +
                ", date='" + date + '\'' +
                '}';
    }
}
