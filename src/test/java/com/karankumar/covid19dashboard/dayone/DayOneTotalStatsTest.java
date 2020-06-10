package com.karankumar.covid19dashboard.dayone;

import com.karankumar.covid19dashboard.testutils.TestUtil;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;

public class DayOneTotalStatsTest {
    private static JSONObject json;

    @BeforeAll
    public static void setupData() {
        json = TestUtil.readJSON(TestUtil.COUNTRY_SUMMARY_FILE_PATH);
    }
}
