package com.karankumar.covid19dashboard.testutils;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestUtil {
    private static final String TEST_RESOURCES = "src/test/resources/";
    public static final String GLOBAL_SUMMARY_FILE_PATH = TEST_RESOURCES + "GlobalSummary.json";
    public static final String COUNTRY_SUMMARY_FILE_PATH = TEST_RESOURCES + "CountrySummary.json";

    public static JSONObject readJSON(String filePath) {
        JSONObject jsonObject = null;
        try {
            String json = new String(Files.readAllBytes(Paths.get(filePath)));
            jsonObject = new JSONObject(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
