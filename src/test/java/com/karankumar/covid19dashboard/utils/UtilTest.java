package com.karankumar.covid19dashboard.utils;

import com.karankumar.covid19dashboard.backend.api.summary.SummaryConst;
import com.karankumar.covid19dashboard.backend.utils.Util;
import com.karankumar.covid19dashboard.testutils.TestUtil;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UtilTest {
    private JSONObject jsonObject;

    @Before
    public void setupData() {
        jsonObject = TestUtil.readJson(TestUtil.GLOBAL_SUMMARY_FILE_PATH);
    }

    // The date should be formatted as dd/MM/yyyy
    @Test
    public void dateCorrectlyFormatted() {
        String date = jsonObject.getString(SummaryConst.DATE);
        Assert.assertEquals("06-06-2020", Util.formatDate(date));
    }
}
