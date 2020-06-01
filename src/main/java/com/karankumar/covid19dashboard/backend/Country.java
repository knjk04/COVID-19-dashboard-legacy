package com.karankumar.covid19dashboard.backend;

public class Country {
    private String countryName;
    private int totalDeaths;
    private int totalRecovered;
    private int totalConfirmedCases;

    public Country(String countryName, int totalDeaths, int totalRecovered, int totalConfirmedCases) {
        this.countryName = countryName;
        this.totalDeaths = totalDeaths;
        this.totalRecovered = totalRecovered;
        this.totalConfirmedCases = totalConfirmedCases;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public int getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(int totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public int getTotalConfirmedCases() {
        return totalConfirmedCases;
    }

    public void setTotalConfirmedCases(int totalConfirmedCases) {
        this.totalConfirmedCases = totalConfirmedCases;
    }
}
