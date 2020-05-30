package com.karankumar.covid19dashboard.backend.global;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GlobalStats {
    private final OkHttpClient client;

    private static final String apiUrl = "https://api.covid19api.com/";
    private static final String apiSummary = "/summary";

    private static Cache<String, JSONObject> cache;

    static {
        cache = Caffeine.newBuilder()
                .expireAfterWrite(24, TimeUnit.HOURS)
                .maximumSize(10_000)
                .build();
    }

    public GlobalStats() {
        client = new OkHttpClient().newBuilder()
                .build();

        if (getTotalCases() == null || getTotalDeaths() == null || getTotalRecovered() == null) {
            fetchSummary();
        } else {
            System.out.println("GlobalStats: none are null");
        }
    }

    private void fetchSummary() {
        Request request = new Request.Builder()
                .url(apiUrl + apiSummary)
                .method("GET", null)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String data = response.body().string();

            System.out.println("Data:\n" + data);

            JSONObject jsonObject = new JSONObject(data);
            JSONObject global = jsonObject.getJSONObject(GlobalConst.GLOBAL);

            System.out.println(global);

            cache.put(GlobalConst.SUMMARY, jsonObject);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     @return The total number of deaths. This may be null
     */
    public Integer getTotalDeaths() {
        JSONObject jsonObject = cache.getIfPresent(GlobalConst.SUMMARY);

        Integer totalDeaths = null;
        if (jsonObject != null) {
            JSONObject global = jsonObject.getJSONObject(GlobalConst.GLOBAL);
            totalDeaths = global.getInt(GlobalConst.TOTAL_DEATHS);
        } else {
            System.out.println("getTotalDeaths(): jsonObject is null");
        }

        if (totalDeaths == null) {
            System.out.println("No total deaths present");
        } else {
            System.out.println("Total deaths present");
        }
        return totalDeaths;
    }

    /**
     @return The total number of people that have recovered. This may be null
     */
    public Integer getTotalRecovered() {
        JSONObject jsonObject = cache.getIfPresent(GlobalConst.SUMMARY);

        Integer totalRecovered = null;
        if (jsonObject != null) {
            JSONObject global = jsonObject.getJSONObject(GlobalConst.GLOBAL);
            totalRecovered = global.getInt(GlobalConst.TOTAL_RECOVERED);
        } else {
            System.out.println("getTotalRecovered(): json object is null");
        }

        if (totalRecovered == null) {
            System.out.println("No total recovered present");
        } else {
            System.out.println("Total recovered present");
        }
        return totalRecovered;
    }

    /**
     @return The total number of confirmed cases. This may be null
     */
    public Integer getTotalCases() {
        JSONObject jsonObject = cache.getIfPresent(GlobalConst.SUMMARY);

        Integer totalCases = null;
        if (jsonObject != null) {
            JSONObject global = jsonObject.getJSONObject(GlobalConst.GLOBAL);
            totalCases = global.getInt(GlobalConst.TOTAL_CONFIRMED_CASES);
        } else {
            System.out.println("getTotalCases(): json object is null");
        }

        if (totalCases == null) {
            System.out.println("No total cases present");
        } else {
            System.out.println("Total cases present");
        }
        return totalCases;
    }
}
