package com.karankumar.covid19dashboard.backend.domain.dayone;

import com.karankumar.covid19dashboard.backend.utils.Util;

public class CountryTotal {
    private final String date;

    public CountryTotal(String date) {
        this.date = Util.formatDate(date);
    }

    public String getDate() {
        return date;
    }
}
