package com.karankumar.covid19dashboard.backend.api.dayone;

import com.karankumar.covid19dashboard.backend.api.util.ApiConst;
import com.karankumar.covid19dashboard.backend.api.util.CountryName;
import com.karankumar.covid19dashboard.backend.domain.CountryTotal;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DayOneTotalStats {
    private static final OkHttpClient client;
    private final String slug;
    private static final Logger logger;

    static {
        client = new OkHttpClient().newBuilder()
                .build();

        logger = Logger.getLogger(DayOneTotalStats.class.getName());
        logger.setLevel(Level.INFO);
    }

    private ArrayList<CountryTotal> countriesLiveStats;

    public DayOneTotalStats(CountryName countryName) {
        this.slug = countryName.getSlug(countryName);

        JSONArray dayOneTotal = fetchDayOneTotalJson();
        if (dayOneTotal != null) {
            storeDayOneTotalStats(dayOneTotal);
        } else {
            logger.log(Level.FINE, "The retrieved day one total is null");
        }
    }

    private JSONArray fetchDayOneTotalJson() {
        String dayOneUrl = DayOneTotalConst.prefixUrl + slug + DayOneTotalConst.suffixUrl;
        String url = ApiConst.apiUrl + dayOneUrl;
        logger.log(Level.INFO, url);

        Request request = new Request.Builder()
                .url(url)
                .method(ApiConst.method, null)
                .build();
        JSONArray jsonArray = null;
        try {
            Response response = client.newCall(request).execute();
            String data = response.body().string();

            logger.log(Level.INFO, "Day One Total data:\n" + data);
            jsonArray = new JSONArray(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    private void storeDayOneTotalStats(JSONArray jsonArray) {
        countriesLiveStats = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int cases = jsonObject.getInt(DayOneTotalConst.CASES);
            String date = jsonObject.getString(DayOneTotalConst.DATE);
            CountryTotal countryLiveStats = new CountryTotal(cases, date);
            countriesLiveStats.add(countryLiveStats);
        }
    }

    public ArrayList<CountryTotal> getLiveCases() {
        return countriesLiveStats;
    }
}
