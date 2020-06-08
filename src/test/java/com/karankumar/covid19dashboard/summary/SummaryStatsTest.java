package com.karankumar.covid19dashboard.summary;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.karankumar.covid19dashboard.backend.api.summary.SummaryConst;
import com.karankumar.covid19dashboard.backend.api.summary.SummaryStats;
import com.karankumar.covid19dashboard.backend.api.util.CountryName;
import com.karankumar.covid19dashboard.testutils.TestUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.TreeMap;

public class SummaryStatsTest {
    private JSONObject jsonObject;
    private SummaryStats summaryStats;

    @Before
    public void setupData() {
        jsonObject = TestUtil.readJSON(TestUtil.GLOBAL_SUMMARY_FILE_PATH);
        summaryStats = new SummaryStats();
        try {
            FieldUtils.writeField(summaryStats, "jsonObject", jsonObject, true);
            summaryStats.getAllCountriesSummary();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if the time is correctly displayed as HH:MM:SS
     */
    @Test
    public void timeCorrectlyFormatted() {
        String timeGroundTruth = "12:26:39";
        Assert.assertEquals(summaryStats.getTime(), timeGroundTruth);
    }

    @Test
    public void checkTotalRecovered() {
        Integer totalRecoveredGroundTruth = 3000504;
        Assert.assertEquals(summaryStats.getTotalRecovered(), totalRecoveredGroundTruth);
    }

    @Test
    public void checkTotalConfirmedCases() {
        Integer totalConfirmedCasesGroundTruth = 6829314;
        Assert.assertEquals(summaryStats.getTotalConfirmedCases(), totalConfirmedCasesGroundTruth);
    }

    @Test
    public void checkTotalDeaths() {
        Integer totalDeathsGroundTruth = 402636;
        Assert.assertEquals(summaryStats.getTotalDeaths(), totalDeathsGroundTruth);
    }

    @Test
    public void checkIfCacheCorrectlyStoresData() {
        try {
            Cache<String, JSONObject> testCache = Caffeine.newBuilder()
                    .build();
            testCache.put(SummaryConst.SUMMARY, jsonObject);

            Field field = SummaryStats.class.getDeclaredField("cache");
            field.setAccessible(true);
            field.set(field, testCache);

            Cache<String, JSONObject> retrievedCache = (Cache<String, JSONObject>) FieldUtils.readStaticField(field);

            Assert.assertEquals(
                    retrievedCache.getIfPresent(SummaryConst.SUMMARY), testCache.getIfPresent(SummaryConst.SUMMARY));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void mostCasesCorrectlySet() {
        TreeMap<Integer, String> mostCasesGroundTruth = new TreeMap<>();
        mostCasesGroundTruth.put(1897380, CountryName.UNITED_STATES_OF_AMERICA.toString());
        mostCasesGroundTruth.put(614941, CountryName.BRAZIL.toString());
        mostCasesGroundTruth.put(449256, CountryName.RUSSIAN_FEDERATION.toString());
        mostCasesGroundTruth.put(284734, CountryName.UNITED_KINGDOM.toString());
        mostCasesGroundTruth.put(240978, CountryName.SPAIN.toString());

        Assert.assertEquals(summaryStats.getMostCases(), mostCasesGroundTruth);
    }
}
