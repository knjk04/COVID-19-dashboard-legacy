package com.karankumar.covid19dashboard.backend.api.dayone;

import com.karankumar.covid19dashboard.backend.api.util.CountryName;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryCasesTotal;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryDeathsTotal;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryTotal;
import com.karankumar.covid19dashboard.testutils.TestUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

class DayOneTotalStatsTest {
    private static DayOneTotalStats<CountryCasesTotal> countryConfirmedStats;
    private static DayOneTotalStats<CountryDeathsTotal> countryDeathStats;

    private static final String DAY_ONE_URL =
            "https://api.covid19api.com/total/dayone/country/united-kingdom/status/";

    @BeforeAll
    public static void setUp() {
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

        assumeThat(confirmedCasesJson).isNotNull();
        assumeThat(deathsJson).isNotNull();
    }

    @Test
    void confirmedUrlIsCorrect() {
        // given
        String urlGroundTruth = DAY_ONE_URL + "confirmed";

        // when
        String actual = countryConfirmedStats.getUrl();

        // then
        assertThat(actual).isEqualTo(urlGroundTruth);
    }

    @Test
    void deathsUrlIsCorrect() {
        // given
        String urlGroundTruth = DAY_ONE_URL + "deaths";

        // when
        String actual = countryDeathStats.getUrl();

        // then
        assertThat(actual).isEqualTo(urlGroundTruth);
    }

    @Test
    void totalConfirmedCasesIsCorrect() {
        // given
        Integer totalConfirmedCasesGroundTruth = 289_140;

        // when
        ArrayList<CountryCasesTotal> confirmedCases = countryConfirmedStats.getTotalConfirmedCases();
        CountryTotal last = confirmedCases.get(confirmedCases.size() - 1);
        Integer actual = last.getNumberOfCases();

        // then
        assertThat(actual).isEqualTo(totalConfirmedCasesGroundTruth);
    }

    @Test
    void totalDeathsIsCorrect() {
        // given
        Integer totalDeathsGroundTruth = 40_883;

        // when
        ArrayList<CountryDeathsTotal> deaths = countryDeathStats.getTotalDeaths();
        CountryTotal last = deaths.get(deaths.size() - 1);
        Integer actual = last.getNumberOfCases();

        // then
        assertThat(actual).isEqualTo(totalDeathsGroundTruth);
    }
}
