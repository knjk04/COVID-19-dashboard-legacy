package com.karankumar.covid19dashboard.dayone;

import com.karankumar.covid19dashboard.backend.api.dayone.CaseType;
import com.karankumar.covid19dashboard.backend.api.dayone.DayOneTotalStats;
import com.karankumar.covid19dashboard.backend.api.util.CountryName;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryCasesTotal;
import com.karankumar.covid19dashboard.testutils.TestUtil;
import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DayOneTotalStatsTest {
    private static DayOneTotalStats<CountryCasesTotal> countryStats;

    @BeforeAll
    public static void setupData() {
        JSONArray json = TestUtil.readJsonArray(TestUtil.COUNTRY_SUMMARY_FILE_PATH);
        countryStats = new DayOneTotalStats(CountryName.UNITED_KINGDOM, CaseType.CONFIMRED);
        if (json == null) {
            System.out.println("null json");
        } else {
            System.out.println("JSON: " + json);
        }

        Assumptions.assumeTrue(json != null);
    }

    @Test
    public void confirmedUrlIsCorrect() {
        String urlGroundTruth = "https://api.covid19api.com/total/dayone/country/united-kingdom/status/confirmed";
        try {
            Method method = countryStats.getClass().getDeclaredMethod("getUrl");
            method.setAccessible(true);
            String url = (String) method.invoke(countryStats);
            Assertions.assertEquals(urlGroundTruth, url);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
