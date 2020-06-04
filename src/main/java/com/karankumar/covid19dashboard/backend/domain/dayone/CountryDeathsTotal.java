package com.karankumar.covid19dashboard.backend.domain.dayone;

public class CountryDeathsTotal extends CountryTotal {
    private int numberOfDeaths;

    public CountryDeathsTotal(int cases, String date) {
        super(date);
        this.numberOfDeaths = cases;
    }

    public int getNumberOfDeaths() {
        return numberOfDeaths;
    }

    @Override
    public String toString() {
        return "CountryDeathsTotal{" +
                "cases=" + numberOfDeaths +
                ", date='" + super.getDate() + '\'' +
                '}';
    }
}
