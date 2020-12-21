package com.karankumar.covid19dashboard.backend.api.dayone;

public class DayOneTotalConst {
    private DayOneTotalConst() {}

    static final String PREFIX_URL = "total/dayone/country/";
    public static final String SUFFIX_CONFIRMED_URL = "/status/confirmed";
    public static final String SUFFIX_DEATHS_URL = "/status/deaths";

    // JSON keys
    static final String CASES = "Cases";
    static final String DATE = "Date";
}
