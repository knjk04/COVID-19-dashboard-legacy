package com.karankumar.covid19stats.backend;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class GlobalStats {
    private final OkHttpClient client;

    private static final String apiUrl = "https://api.covid19api.com/";
    private static final String apiSummary = "/summary";

    public GlobalStats() {
        client = new OkHttpClient().newBuilder()
                .build();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getTotalDeaths() {
        // TODO: Implement
    }

    private void getTotalCases() {
        // TODO: Implement
    }

    private void getTotalRecovered() {
        // TODO: Implement
    }
}
