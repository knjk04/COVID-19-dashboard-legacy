package com.karankumar.covid19dashboard.backend.api.summary;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.karankumar.covid19dashboard.backend.api.util.ApiConst;
import com.karankumar.covid19dashboard.backend.domain.CountrySummary;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class SummaryStats {
    private static final OkHttpClient client;
    private static Cache<String, JSONObject> cache;
    private JSONObject jsonObject;

    private TreeMap<Integer, String> mostCases = new TreeMap<>();
    private TreeMap<Integer, String> mostDeaths = new TreeMap<>();

    static {
        cache = Caffeine.newBuilder()
                .expireAfterWrite(24, TimeUnit.HOURS)
                .maximumSize(10_000)
                .build();

        client = new OkHttpClient().newBuilder()
                .build();
    }

    public SummaryStats() {
        if (getTotalCases() == null || getTotalDeaths() == null || getTotalRecovered() == null) {
            jsonObject = cache.getIfPresent(SummaryConst.SUMMARY);
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
            JSONObject global = jsonObject.getJSONObject(SummaryConst.GLOBAL);

            System.out.println(global);

            cache.put(SummaryConst.SUMMARY, jsonObject);

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
            JSONObject global = jsonObject.getJSONObject(SummaryConst.GLOBAL);
            totalDeaths = global.getInt(SummaryConst.TOTAL_DEATHS);
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
            JSONObject global = jsonObject.getJSONObject(SummaryConst.GLOBAL);
            totalRecovered = global.getInt(SummaryConst.TOTAL_RECOVERED);
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
            JSONObject global = jsonObject.getJSONObject(SummaryConst.GLOBAL);
            totalCases = global.getInt(SummaryConst.TOTAL_CONFIRMED_CASES);
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
            String dateAndTime = jsonObject.getString(SummaryConst.DATE);
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
            String dateAndTime = jsonObject.getString(SummaryConst.DATE);
            String[] split = dateAndTime.split("T");
            time = split[1].substring(0, split[1].length()-1);
        }
        return time;
    }

    public ArrayList<CountrySummary> getAllCountriesSummary() {
        ArrayList<CountrySummary> countriesList = new ArrayList<>();

        if (jsonObject != null) {
            JSONArray countries = jsonObject.getJSONArray(SummaryConst.COUNTRIES);
            for (int i = 0; i < countries.length(); i++) {
                JSONObject jsonCountry = countries.getJSONObject(i);

                String countryName = jsonCountry.getString(SummaryConst.COUNTRY);
                int totalConfirmedCases = jsonCountry.getInt(SummaryConst.TOTAL_CONFIRMED_CASES);
                int totalDeaths = jsonCountry.getInt(SummaryConst.TOTAL_DEATHS);
                int totalRecovered = jsonCountry.getInt(SummaryConst.TOTAL_RECOVERED);
                int totalNewCases = jsonCountry.getInt(SummaryConst.NEW_CONFIRMED_CASES);
                int totalNewDeaths = jsonCountry.getInt(SummaryConst.NEW_DEATHS);
                int totalNewRecovered = jsonCountry.getInt(SummaryConst.NEW_RECOVERED);
                CountrySummary country = new CountrySummary(
                        countryName,
                        totalDeaths,
                        totalRecovered,
                        totalConfirmedCases,
                        totalNewCases,
                        totalNewDeaths,
                        totalNewRecovered
                );
                countriesList.add(country);
                populateMostCases(totalConfirmedCases, countryName);
                populateMostDeaths(totalDeaths, countryName);
            }
        }
        return countriesList;
    }

    // This should only be called from within getAllCountriesSummary
    private void populateMostCases(int totalConfirmedCases, String countryName) {
        if (mostCases.size() < ApiConst.MOST_CONFIRMED_CASES) {
            mostCases.put(totalConfirmedCases, countryName);
        } else if (mostCases.firstKey() < totalConfirmedCases) {
            // swap with this new country that has more cases
            mostCases.remove(mostCases.firstKey());
            mostCases.put(totalConfirmedCases, countryName);
        }
    }

    // This should only be called from within getAllCountriesSummary
    private void populateMostDeaths(int totalDeaths, String countryName) {
        if (mostDeaths.size() < ApiConst.MOST_DEATHS) {
            mostDeaths.put(totalDeaths, countryName);
        } else if (mostDeaths.firstKey() < totalDeaths) {
            // swap with this new country that has more deaths
            mostDeaths.remove(mostDeaths.firstKey());
            mostDeaths.put(totalDeaths, countryName);
        }
    }

    /**
     * @return a TreeMap containing the countries with the most cases.
     * The Integer key is the number of cases and the String value is the country name
     */
    public TreeMap<Integer, String> getMostCases() {
        if (mostCases.size() == 0) {
            System.out.println("ApiStats: Empty most cases TreeMap");
        }
        return mostCases;
    }

    /**
     * @return a TreeMap containing the countries with the most deaths.
     * The Integer key is the number of deaths and the String value is the country name
     */
    public TreeMap<Integer, String> getMostDeaths() {
        if (mostDeaths.size() == 0) {
            System.out.println("ApiStats: Empty most deaths TreeMap");
        }
        return mostDeaths;
    }
}
