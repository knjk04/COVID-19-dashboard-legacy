package com.karankumar.covid19dashboard.testutils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestUtil {
    private static final String TEST_RESOURCES = "src/test/resources/";
    public static final String GLOBAL_SUMMARY_FILE_PATH = TEST_RESOURCES + "GlobalSummary.json";
    public static final String COUNTRY_CONFIRMED_FILE_PATH = TEST_RESOURCES + "CountryConfirmedCases.json";
    public static final String COUNTRY_DEATHS_FILE_PATH = TEST_RESOURCES + "CountryDeaths.json";

    public static JSONObject readJson(String filePath) {
        JSONObject jsonObject = null;
        try {
            String json = new String(Files.readAllBytes(Paths.get(filePath)));
            jsonObject = new JSONObject(json);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONArray readJsonArray(String filePath) {
        JSONArray jsonArray = null;
        try {
            String json = new String(Files.readAllBytes(Paths.get(filePath)));
            jsonArray = new JSONArray(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
}
