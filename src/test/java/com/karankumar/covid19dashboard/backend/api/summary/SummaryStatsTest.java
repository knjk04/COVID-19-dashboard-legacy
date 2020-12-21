package com.karankumar.covid19dashboard.backend.api.summary;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.karankumar.covid19dashboard.backend.api.util.CountryName;
import com.karankumar.covid19dashboard.testutils.TestUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.TreeMap;

class SummaryStatsTest {
    private static JSONObject jsonObject;
    private static SummaryStats summaryStats;

    @BeforeAll
    public static void setupData() {
        jsonObject = TestUtil.readJson(TestUtil.GLOBAL_SUMMARY_FILE_PATH);
        summaryStats = new SummaryStats();
        try {
            FieldUtils.writeField(summaryStats, "jsonObject", jsonObject, true);
            summaryStats.getAllCountriesSummary();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        // only run the tests if the json object is not null
        Assumptions.assumeTrue(jsonObject != null);
    }

    /**
     * Check if the time is correctly displayed as HH:MM:SS
     */
    @Test
    void timeCorrectlyFormatted() {
        String timeGroundTruth = "12:26:39";
        Assertions.assertEquals(summaryStats.getTime(), timeGroundTruth);
    }

    @Test
    void checkTotalRecovered() {
        Integer totalRecoveredGroundTruth = 3_000_504;
        Assertions.assertEquals(summaryStats.getTotalRecovered(), totalRecoveredGroundTruth);
    }

    @Test
    void checkTotalConfirmedCases() {
        Integer totalConfirmedCasesGroundTruth = 6_829_314;
        Assertions.assertEquals(summaryStats.getTotalConfirmedCases(), totalConfirmedCasesGroundTruth);
    }

    @Test
    void checkTotalDeaths() {
        Integer totalDeathsGroundTruth = 402_636;
        Assertions.assertEquals(summaryStats.getTotalDeaths(), totalDeathsGroundTruth);
    }

    @Test
    void checkIfCacheCorrectlyStoresData() {
        try {
            Cache<String, JSONObject> testCache = Caffeine.newBuilder()
                    .build();
            testCache.put(SummaryConst.SUMMARY, jsonObject);

            Field field = SummaryStats.class.getDeclaredField("cache");
            field.setAccessible(true);
            field.set(field, testCache);

            Cache<String, JSONObject> retrievedCache = (Cache<String, JSONObject>) FieldUtils.readStaticField(field);

            Assertions.assertEquals(
                    retrievedCache.getIfPresent(SummaryConst.SUMMARY), testCache.getIfPresent(SummaryConst.SUMMARY));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Test
    void mostCasesCorrectlySet() {
        TreeMap<Integer, String> mostCasesGroundTruth = new TreeMap<>();
        mostCasesGroundTruth.put(1_897_380, CountryName.UNITED_STATES_OF_AMERICA.toString());
        mostCasesGroundTruth.put(614_941, CountryName.BRAZIL.toString());
        mostCasesGroundTruth.put(449_256, CountryName.RUSSIAN_FEDERATION.toString());
        mostCasesGroundTruth.put(284_734, CountryName.UNITED_KINGDOM.toString());
        mostCasesGroundTruth.put(240_978, CountryName.SPAIN.toString());

        Assertions.assertEquals(summaryStats.getMostCases(), mostCasesGroundTruth);
    }

    @Test
    void mostDeathsCorrectlySet() {
        TreeMap<Integer, String> mostDeathsGroundTruth = new TreeMap<>();
        mostDeathsGroundTruth.put(109_132, CountryName.UNITED_STATES_OF_AMERICA.toString());
        mostDeathsGroundTruth.put(40_344, CountryName.UNITED_KINGDOM.toString());
        mostDeathsGroundTruth.put(34_021, CountryName.BRAZIL.toString());
        mostDeathsGroundTruth.put(33_774, CountryName.ITALY.toString());
        mostDeathsGroundTruth.put(29_114, CountryName.FRANCE.toString());
        Assertions.assertEquals(summaryStats.getMostDeaths(), mostDeathsGroundTruth);
    }
}
