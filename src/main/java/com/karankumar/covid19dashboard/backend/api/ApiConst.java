package com.karankumar.covid19dashboard.backend.api;

public final class ApiConst {
    public static final String NEW_CONFIRMED_CASES = "NewConfirmed";
    public static final String NEW_DEATHS = "NewDeaths";
    public static final String NEW_RECOVERED = "NewRecovered";
    static final String apiUrl = "https://api.covid19api.com/";
    static final String apiSummary = "/summary";

    // JSON keys
    static final String COUNTRIES = "Countries";
    static final String COUNTRY = "Country";
    static final String DATE = "Date";
    static final String GLOBAL = "Global";
    static final String SUMMARY = "Summary";
    static final String TOTAL_CONFIRMED_CASES = "TotalConfirmed";
    static final String TOTAL_DEATHS = "TotalDeaths";
    static final String TOTAL_RECOVERED = "TotalRecovered";

    public static final int MOST_CONFIRMED_CASES = 5;
}
