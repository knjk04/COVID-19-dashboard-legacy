package com.karankumar.covid19dashboard.backend.domain;

public class CountrySummary {
    private final String countryName;
    private final int totalDeaths;
    private final int totalRecovered;
    private final int totalConfirmedCases;
    private final int totalNewCases;
    private final int totalNewDeaths;
    private final int totalNewRecovered;

    public CountrySummary(String countryName, int totalDeaths, int totalRecovered, int totalConfirmedCases,
                          int totalNewCases, int totalNewDeaths, int totalNewRecovered) {
        this.countryName = countryName;
        this.totalDeaths = totalDeaths;
        this.totalRecovered = totalRecovered;
        this.totalConfirmedCases = totalConfirmedCases;
        this.totalNewCases = totalNewCases;
        this.totalNewDeaths = totalNewDeaths;
        this.totalNewRecovered = totalNewRecovered;
    }

    public String getCountryName() {
        return countryName;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public int getTotalRecovered() {
        return totalRecovered;
    }

    public int getTotalConfirmedCases() {
        return totalConfirmedCases;
    }

    public int getTotalNewCases() {
        return totalNewCases;
    }

    public int getTotalNewDeaths() {
        return totalNewDeaths;
    }

    public int getTotalNewRecovered() {
        return totalNewRecovered;
    }
}
