package com.karankumar.covid19dashboard.backend.domain.dayone;

import com.karankumar.covid19dashboard.backend.utils.Util;

public class CountryTotal {
    // this should of type String, not of type CountryName
    private String countryName;

    private final String date;

    public CountryTotal(String countryName, String date) {
        this.countryName = countryName;
        this.date = Util.formatDate(date);
    }

    public String getDate() {
        return date;
    }

    public String getCountryName() {
        return countryName;
    }
}
