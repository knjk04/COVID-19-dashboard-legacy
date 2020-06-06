package com.karankumar.covid19dashboard.utils;

import com.karankumar.covid19dashboard.backend.api.summary.SummaryConst;
import com.karankumar.covid19dashboard.backend.utils.Util;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UtilTest {
    private JSONObject jsonObject;

    @Before
    public void setupData() throws IOException {
        jsonObject = readJSON();
    }

    private JSONObject readJSON() throws IOException {
        String filePath = "src/test/resources/GlobalSummaryJson.json";
        String json = new String(Files.readAllBytes(Paths.get(filePath)));
        return new JSONObject(json);
    }

    // The date should be formatted as dd/MM/yyyy
    @Test
    public void dateCorrectlyFormatted() {
        String date = jsonObject.getString(SummaryConst.DATE);
        Assert.assertEquals("06-06-2020", Util.formatDate(date));
    }
}
