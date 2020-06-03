package com.karankumar.covid19dashboard.backend.api.dayonelive;

import com.karankumar.covid19dashboard.backend.api.util.ApiConst;
import com.karankumar.covid19dashboard.backend.domain.CountryLive;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DayOneLiveStats {
    private static final OkHttpClient client;
    private final String slug;
    private static final Logger logger;

    static {
        client = new OkHttpClient().newBuilder()
                .build();

        logger = Logger.getLogger(DayOneLiveStats.class.getName());
        logger.setLevel(Level.INFO);
    }

    private ArrayList<CountryLive> countriesLiveStats;

    /**
     * @param slug a slug name (refer to the API) for a country
     */
    public DayOneLiveStats(String slug) {
        this.slug = slug;
        fetchDayOneLive();
    }

    private void fetchDayOneLive() {
        String dayOneUrl = DayOneLiveConst.prefixUrl + slug + DayOneLiveConst.confirmedLiveUrl;
        Request request = new Request.Builder()
                .url(ApiConst.apiUrl + dayOneUrl)
                .method(ApiConst.method, null)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String data = response.body().string();

            logger.log(Level.INFO, "Day One Live data:\n" + data);

            JSONArray jsonArray = new JSONArray(data);

            countriesLiveStats = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int cases = jsonObject.getInt(DayOneLiveConst.CASES);
                String date = jsonObject.getString(DayOneLiveConst.DATE);
                CountryLive countryLiveStats = new CountryLive(cases, date);
                countriesLiveStats.add(countryLiveStats);
            }

//            for (CountryLive c : countriesLiveStats) {
//                System.out.println("Country: " + c);
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<CountryLive> getLiveCases() {
        return countriesLiveStats;
    }
}
