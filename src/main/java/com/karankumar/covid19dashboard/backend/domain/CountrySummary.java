package com.karankumar.covid19dashboard.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CountrySummary {
    private final String countryName;
    private final int totalDeaths;
    private final int totalRecovered;
    private final int totalConfirmedCases;
    private final int totalNewCases;
    private final int totalNewDeaths;
    private final int totalNewRecovered;
}
