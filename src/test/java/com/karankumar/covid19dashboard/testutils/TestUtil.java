package com.karankumar.covid19dashboard.testutils;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestUtil {
    public static final String GLOBAL_SUMMARY_FILE_PATH = "src/test/resources/GlobalSummaryJson.json";

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
