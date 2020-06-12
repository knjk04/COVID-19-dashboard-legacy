package com.karankumar.covid19dashboard.backend.api.dayone;

import com.karankumar.covid19dashboard.backend.api.util.CountryName;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryCasesTotal;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryDeathsTotal;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryTotal;
import com.karankumar.covid19dashboard.testutils.TestUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class DayOneTotalStatsTest {
    private static DayOneTotalStats<CountryCasesTotal> countryConfirmedStats;
    private static DayOneTotalStats<CountryDeathsTotal> countryDeathStats;

    @BeforeAll
    public static void setupData() {
        JSONArray confirmedCasesJson = TestUtil.readJsonArray(TestUtil.COUNTRY_CONFIRMED_FILE_PATH);
        countryConfirmedStats = new DayOneTotalStats(CountryName.UNITED_KINGDOM, CaseType.CONFIMRED);

        JSONArray deathsJson = TestUtil.readJsonArray(TestUtil.COUNTRY_DEATHS_FILE_PATH);
        countryDeathStats = new DayOneTotalStats(CountryName.UNITED_KINGDOM, CaseType.DEATHS);

        try {
            Method fetchDayOneTotalConfirmed = countryConfirmedStats.getClass()
                    .getDeclaredMethod("fetchDayOneTotal", JSONArray.class, CaseType.class);
            fetchDayOneTotalConfirmed.setAccessible(true);
            ArrayList<CountryCasesTotal> caseTotal = (ArrayList<CountryCasesTotal>) fetchDayOneTotalConfirmed
                    .invoke(countryConfirmedStats, confirmedCasesJson, CaseType.CONFIMRED);
            FieldUtils.writeField(countryConfirmedStats, "caseTotals", caseTotal, true);

            Method fetchDayOneTotalDeaths = countryDeathStats.getClass()
                    .getDeclaredMethod("fetchDayOneTotal", JSONArray.class, CaseType.class);
            fetchDayOneTotalDeaths.setAccessible(true);
            ArrayList<CountryDeathsTotal> deathTotal = (ArrayList<CountryDeathsTotal>) fetchDayOneTotalDeaths
                    .invoke(countryDeathStats, deathsJson, CaseType.DEATHS);
            FieldUtils.writeField(countryDeathStats, "deathTotals", deathTotal, true);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        Assumptions.assumeTrue(confirmedCasesJson != null && deathsJson != null);
    }

    @Test
    public void confirmedUrlIsCorrect() {
        String urlGroundTruth = "https://api.covid19api.com/total/dayone/country/united-kingdom/status/confirmed";
        try {
            Method method = countryConfirmedStats.getClass().getDeclaredMethod("getUrl");
            method.setAccessible(true);
            String url = (String) method.invoke(countryConfirmedStats);
            Assertions.assertEquals(urlGroundTruth, url);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deathsUrlIsCorrect() {
        String urlGroundTruth = "https://api.covid19api.com/total/dayone/country/united-kingdom/status/deaths";
        try {
            Method method = countryDeathStats.getClass().getDeclaredMethod("getUrl");
            method.setAccessible(true);
            String url = (String) method.invoke(countryDeathStats);
            Assertions.assertEquals(urlGroundTruth, url);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void totalConfirmedCasesIsCorrect() {
        Integer totalConfirmedCasesGroundTruth = 289_140;
        ArrayList<CountryCasesTotal> confirmedCases = countryConfirmedStats.getTotalConfirmedCases();
        CountryTotal last = confirmedCases.get(confirmedCases.size() - 1);
        Assertions.assertEquals(totalConfirmedCasesGroundTruth, last.getNumberOfCases());
    }

    @Test
    public void totalDeathsIsCorrect() {
        Integer totalDeathsGroundTruth = 40_883;
        ArrayList<CountryDeathsTotal> deaths = countryDeathStats.getTotalDeaths();
        CountryTotal last = deaths.get(deaths.size() - 1);
        Assertions.assertEquals(totalDeathsGroundTruth, last.getNumberOfCases());
    }
}
