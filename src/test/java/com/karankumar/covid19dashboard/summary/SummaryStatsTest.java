package com.karankumar.covid19dashboard.summary;

import com.karankumar.covid19dashboard.backend.api.summary.SummaryStats;
import com.karankumar.covid19dashboard.testutils.TestUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SummaryStatsTest {
    private JSONObject jsonObject;
    private SummaryStats summaryStats;

    @Before
    public void setupData() {
        jsonObject = TestUtil.readJSON(TestUtil.GLOBAL_SUMMARY_FILE_PATH);
        summaryStats = new SummaryStats();
        try {
            FieldUtils.writeField(summaryStats, "jsonObject", jsonObject, true);
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
}
