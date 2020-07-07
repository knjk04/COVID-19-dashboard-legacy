package com.karankumar.covid19dashboard.backend.api.summary;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.karankumar.covid19dashboard.backend.api.util.ApiConst;
import com.karankumar.covid19dashboard.backend.domain.CountrySummary;
import com.karankumar.covid19dashboard.backend.utils.Util;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SummaryStats {
    private static final OkHttpClient client;
    private static Cache<String, JSONObject> cache;
    private JSONObject jsonObject;

    private TreeMap<Integer, String> mostCases = new TreeMap<>();
    private TreeMap<Integer, String> mostDeaths = new TreeMap<>();

    private static final Logger LOGGER = Logger.getLogger(SummaryStats.class.getName());

    static {
        cache = Caffeine.newBuilder()
                .expireAfterWrite(24, TimeUnit.HOURS)
                .maximumSize(10_000)
                .build();

        client = new OkHttpClient().newBuilder()
                .build();
    }

    public SummaryStats() {
        if (getTotalConfirmedCases() == null || getTotalDeaths() == null || getTotalRecovered() == null) {
            jsonObject = cache.getIfPresent(SummaryConst.SUMMARY);
            fetchSummary();
        } else {
            LOGGER.log(Level.SEVERE, "None are null");
        }
    }

    private void fetchSummary() {
        Request request = new Request.Builder()
                .url(ApiConst.apiUrl + ApiConst.apiSummary)
                .method(ApiConst.method, null)
                .build();

        LOGGER.log(Level.INFO, "URL: " + ApiConst.apiUrl + ApiConst.apiSummary);
        try {
            Response response = client.newCall(request).execute();
            String data = response.body().string();

            LOGGER.log(Level.INFO, "Summary data: " + data + "\n");

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
            LOGGER.log(Level.INFO, "getTotalDeaths(): jsonObject is null");
        }

        if (totalDeaths == null) {
            LOGGER.log(Level.INFO, "No total deaths present");
        } else {
            LOGGER.log(Level.INFO, "Total deaths present");
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
            LOGGER.log(Level.SEVERE, "getTotalRecovered(): json object is null");
        }

        if (totalRecovered == null) {
            LOGGER.log(Level.SEVERE, "No total recovered present");
        }
        return totalRecovered;
    }

    /**
     @return The total number of confirmed cases. This may be null
     */
    public Integer getTotalConfirmedCases() {
        Integer totalCases = null;
        if (jsonObject != null) {
            JSONObject global = jsonObject.getJSONObject(SummaryConst.GLOBAL);
            totalCases = global.getInt(SummaryConst.TOTAL_CONFIRMED_CASES);
        } else {
            System.out.println("getTotalCases(): json object is null");
        }

        if (totalCases == null) {
            LOGGER.log(Level.SEVERE, "No total cases present");
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
            date = Util.formatDate(dateAndTime);
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
            LOGGER.log(Level.WARNING, "ApiStats: Empty most cases TreeMap");
        }
        return mostCases;
    }

    /**
     * @return a TreeMap containing the countries with the most deaths.
     * The Integer key is the number of deaths and the String value is the country name
     */
    public TreeMap<Integer, String> getMostDeaths() {
        if (mostDeaths.size() == 0) {
            LOGGER.log(Level.WARNING, "ApiStats: Empty most deaths TreeMap");
        }
        return mostDeaths;
    }
}
