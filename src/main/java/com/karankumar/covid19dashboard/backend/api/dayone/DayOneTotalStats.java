package com.karankumar.covid19dashboard.backend.api.dayone;

import com.karankumar.covid19dashboard.backend.api.util.ApiConst;
import com.karankumar.covid19dashboard.backend.api.util.CountryName;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryCasesTotal;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryDeathsTotal;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryTotal;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

//public class DayOneTotalStats {
public class DayOneTotalStats <T extends CountryTotal> {
    private static final OkHttpClient client;
    private static final Logger logger;

    private final String slug;
    private final CaseType caseType;
    private ArrayList<CountryCasesTotal> caseTotals;

    static {
        client = new OkHttpClient().newBuilder()
                .build();

        logger = Logger.getLogger(DayOneTotalStats.class.getName());
        logger.setLevel(Level.INFO);
    }

    public DayOneTotalStats(CountryName countryName, CaseType caseType) {
        this.slug = countryName.getSlug(countryName);
        this.caseType = caseType;

        JSONArray dayOneTotal = fetchDayOneTotalJson();
        if (dayOneTotal != null) {
            switch (caseType) {
                case CONFIMRED:
                    this.caseTotals = (ArrayList<CountryCasesTotal>) fetchDayOneTotal(dayOneTotal, CaseType.CONFIMRED);
                    break;
                case DEATHS:
                    fetchDayOneTotal(dayOneTotal, CaseType.DEATHS);
                    break;
            }
        } else {
            logger.log(Level.FINE, "The retrieved day one total is null");
        }
    }

    private JSONArray fetchDayOneTotalJson() {
        String url = getUrl();
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

    private String getUrl() {
        String dayOneUrl = ApiConst.apiUrl + DayOneTotalConst.prefixUrl + slug;
        switch (caseType) {
            case CONFIMRED:
                dayOneUrl += DayOneTotalConst.suffixConfirmedUrl;
                break;
            case DEATHS:
                dayOneUrl += DayOneTotalConst.suffixDeathsUrl;
                break;
            case RECOVERED:
                // TODO: implement
                break;
            default:
                logger.log(Level.FINEST, "A new case type has been added but is not handled");
        }
        return dayOneUrl;
    }

//    private ArrayList<CountryCasesTotal> fetchDayOneTotal(JSONArray jsonArray) {
//        ArrayList<CountryCasesTotal> caseTotals = new ArrayList<>();
//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONObject jsonObject = jsonArray.getJSONObject(i);
//            String date = jsonObject.getString(DayOneTotalConst.DATE);
//
//            int cases = jsonObject.getInt(DayOneTotalConst.CASES);
//            CountryCasesTotal countryLiveStats = new CountryCasesTotal(cases, date);
//
//            caseTotals.add(countryLiveStats);
//        }
//        return caseTotals;
//    }

    private ArrayList<T> fetchDayOneTotal(JSONArray jsonArray, CaseType caseType) {
        ArrayList<T> caseTotals = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String date = jsonObject.getString(DayOneTotalConst.DATE);

            if (caseType == CaseType.CONFIMRED) {
                int cases = jsonObject.getInt(DayOneTotalConst.CASES);
                CountryCasesTotal countryCases = new CountryCasesTotal(cases, date);
                caseTotals.add((T) countryCases);
            } else {
                int deaths = jsonObject.getInt(DayOneTotalConst.CASES);
                CountryDeathsTotal countryDeaths = new CountryDeathsTotal(deaths, date);
                caseTotals.add((T) countryDeaths);
            }
        }
        return caseTotals;
    }

    public ArrayList<CountryCasesTotal> getTotalCases() {
        return caseTotals;
    }
}
