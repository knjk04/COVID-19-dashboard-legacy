package com.karankumar.covid19dashboard.backend;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class GlobalStats {
    private final OkHttpClient client;

    private static final String apiUrl = "https://api.covid19api.com/";
    private static final String apiSummary = "/summary";

    private int totalDeaths;
    private int totalRecovered;
    private int totalCases;

    public GlobalStats() {
        client = new OkHttpClient().newBuilder()
                .build();

        fetchSummary();
    }

    private void fetchSummary() {
        Request request = new Request.Builder()
                .url(apiUrl + apiSummary)
                .method("GET", null)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String data = response.body().string();

            System.out.println(data);

            JSONObject jsonObject = new JSONObject(data);
            JSONObject global = jsonObject.getJSONObject("Global");

            System.out.println(global);

            totalDeaths = global.getInt("TotalDeaths");
            totalRecovered = global.getInt("TotalRecovered");
            totalCases = global.getInt("TotalConfirmed");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public int getTotalRecovered() {
        return totalRecovered;
    }

    public int getTotalCases() {
        return totalCases;
    }
}
