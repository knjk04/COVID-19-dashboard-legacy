package com.karankumar.covid19dashboard.backend.api;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.karankumar.covid19dashboard.backend.Country;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

public class ApiStats {
    private static final OkHttpClient client;
    private static Cache<String, JSONObject> cache;
    private JSONObject jsonObject;

    static {
        cache = Caffeine.newBuilder()
                .expireAfterWrite(24, TimeUnit.HOURS)
                .maximumSize(10_000)
                .build();

        client = new OkHttpClient().newBuilder()
                .build();
    }

    public ApiStats() {
        if (getTotalCases() == null || getTotalDeaths() == null || getTotalRecovered() == null) {
            jsonObject = cache.getIfPresent(ApiConst.SUMMARY);
            fetchSummary();
        } else {
            System.out.println("GlobalStats: none are null");
        }
    }

    private void fetchSummary() {
        Request request = new Request.Builder()
                .url(ApiConst.apiUrl + ApiConst.apiSummary)
                .method("GET", null)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String data = response.body().string();

            System.out.println("Data:\n" + data);

            JSONObject jsonObject = new JSONObject(data);
            JSONObject global = jsonObject.getJSONObject(ApiConst.GLOBAL);

            System.out.println(global);

            cache.put(ApiConst.SUMMARY, jsonObject);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     @return The total number of deaths. This may be null
     */
    public Integer getTotalDeaths() {
        Integer totalDeaths = null;
        if (jsonObject != null) {
            JSONObject global = jsonObject.getJSONObject(ApiConst.GLOBAL);
            totalDeaths = global.getInt(ApiConst.TOTAL_DEATHS);
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
        Integer totalRecovered = null;
        if (jsonObject != null) {
            JSONObject global = jsonObject.getJSONObject(ApiConst.GLOBAL);
            totalRecovered = global.getInt(ApiConst.TOTAL_RECOVERED);
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
        Integer totalCases = null;
        if (jsonObject != null) {
            JSONObject global = jsonObject.getJSONObject(ApiConst.GLOBAL);
            totalCases = global.getInt(ApiConst.TOTAL_CONFIRMED_CASES);
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

    /**
     * @return a String representing a date of the form dd-MM-yyyy
     */
    public String getDate() {
        String date = "";
        if (jsonObject != null) {
            String dateAndTime = jsonObject.getString(ApiConst.DATE);
            String[] split = dateAndTime.split("T");
            date = split[0];

            String[] yearMonthDay = date.split("-");
            String year = yearMonthDay[0];
            String month = yearMonthDay[1];
            String day = yearMonthDay[2];
            String[] dayMonthYear = {day, month, year};

            StringJoiner joiner = new StringJoiner("-");
            for(String s : dayMonthYear) {
                joiner.add(s);
            }

            date = joiner.toString();
        }
        return date;
    }

    public String getTime() {
        String time = "";
        if (jsonObject != null) {
            String dateAndTime = jsonObject.getString(ApiConst.DATE);
            String[] split = dateAndTime.split("T");
            time = split[1].substring(0, split[1].length()-1);
        }
        return time;
    }

    public ArrayList<Country> getAllCountriesSummary() {
        ArrayList<Country> countriesList = new ArrayList<>();

        if (jsonObject != null) {
            JSONArray countries = jsonObject.getJSONArray(ApiConst.COUNTRIES);
            for (int i = 0; i < countries.length(); i++) {
                JSONObject jsonCountry = countries.getJSONObject(i);

                String countryName = jsonCountry.getString(ApiConst.COUNTRY);
                int totalConfirmedCases = jsonCountry.getInt(ApiConst.TOTAL_CONFIRMED_CASES);
                int totalDeaths = jsonCountry.getInt(ApiConst.TOTAL_DEATHS);
                int totalRecovered = jsonCountry.getInt(ApiConst.TOTAL_RECOVERED);
                int totalNewCases = jsonCountry.getInt(ApiConst.NEW_CONFIRMED_CASES);
                int totalNewDeaths = jsonCountry.getInt(ApiConst.NEW_DEATHS);
                int totalNewRecovered = jsonCountry.getInt(ApiConst.NEW_RECOVERED);
                Country country = new Country(
                        countryName,
                        totalDeaths,
                        totalRecovered,
                        totalConfirmedCases,
                        totalNewCases,
                        totalNewDeaths,
                        totalNewRecovered
                );
                countriesList.add(country);
            }
        }
        return countriesList;
    }
}
