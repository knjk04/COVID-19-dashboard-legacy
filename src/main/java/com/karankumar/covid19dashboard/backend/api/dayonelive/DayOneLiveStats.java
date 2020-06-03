package com.karankumar.covid19dashboard.backend.api.dayonelive;

import com.karankumar.covid19dashboard.backend.domain.CountryLive;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class DayOneLiveStats {
    private static final OkHttpClient client;

    static {
        client = new OkHttpClient().newBuilder()
                .build();
    }

    public DayOneLiveStats() {

    }

    // TODO: make private
    public void fetchDayOneLive() {
        Request request = new Request.Builder()
                .url("https://api.covid19api.com/dayone/country/south-africa/status/confirmed/live")
                .method("GET", null)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String data = response.body().string();

            System.out.println("Day One Live data:\n" + data);

            JSONArray jsonArray = new JSONArray(data);
            System.out.println("Json Array: " + jsonArray);

            ArrayList<CountryLive> countriesLiveStats = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int cases = jsonObject.getInt("Cases");
                String date = jsonObject.getString("Date");
                CountryLive countryLiveStats = new CountryLive(cases, date);
                countriesLiveStats.add(countryLiveStats);
            }

            for (CountryLive c : countriesLiveStats) {
                System.out.println("Country: " + c);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
