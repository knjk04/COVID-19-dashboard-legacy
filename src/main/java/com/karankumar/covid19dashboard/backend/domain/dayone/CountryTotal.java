package com.karankumar.covid19dashboard.backend.domain.dayone;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CountryTotal {
    // this should of type String, not of type CountryName
    private String countryName;

    private final String date;
    private final Integer numberOfCases;
}
