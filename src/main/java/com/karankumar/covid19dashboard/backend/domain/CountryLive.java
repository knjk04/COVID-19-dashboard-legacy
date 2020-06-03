package com.karankumar.covid19dashboard.backend.domain;

public class CountryLive {
    // From JSON
    private int cases;
    private String date;

    public CountryLive(int cases, String date) {
        this.cases = cases;
        this.date = date;
    }

    public int getCases() {
        return cases;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "CountryLive{" +
                "cases=" + cases +
                ", date='" + date + '\'' +
                '}';
    }
}
