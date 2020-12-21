package com.karankumar.covid19dashboard.backend.utils;

import com.karankumar.covid19dashboard.backend.api.summary.SummaryConst;
import com.karankumar.covid19dashboard.testutils.TestUtil;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilTest {
    private JSONObject jsonObject;

    @Before
    public void setUp() {
        jsonObject = TestUtil.readJson(TestUtil.GLOBAL_SUMMARY_FILE_PATH);
    }

    // The date should be formatted as dd/MM/yyyy
    @Test
    public void dateCorrectlyFormatted() {
        String expected = "06-06-2020";
        String date = jsonObject.getString(SummaryConst.DATE);

        // when
        String actual = DateUtil.formatDate(date);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
