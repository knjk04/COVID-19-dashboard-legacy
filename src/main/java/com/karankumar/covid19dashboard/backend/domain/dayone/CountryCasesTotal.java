package com.karankumar.covid19dashboard.backend.domain.dayone;

public class CountryCasesTotal extends CountryTotal {
    private int numberOfCases;

    public CountryCasesTotal(int cases, String date, String countryName) {
        super(countryName, date);
        this.numberOfCases = cases;
    }

    public int getNumberOfCases() {
        return numberOfCases;
    }

    @Override
    public String toString() {
        return "CountryCasesTotal{" +
                "country=" + super.getCountryName() +
                ", cases=" + numberOfCases +
                ", date='" + getDate() + '\'' +
                '}';
    }
}
