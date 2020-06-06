package com.karankumar.covid19dashboard.ui.views.country;

import com.karankumar.covid19dashboard.backend.api.dayone.CaseType;
import com.karankumar.covid19dashboard.backend.api.dayone.DayOneTotalStats;
import com.karankumar.covid19dashboard.backend.api.util.CountryName;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryCasesTotal;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryDeathsTotal;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryTotal;
import com.vaadin.flow.component.charts.Chart;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CasesAndDeathsView <T extends CountryTotal> extends BaseCaseView<T> {
    private static final Logger logger = Logger.getLogger(CountryView.class.getName());

    private Number[] confirmedCases;
    private ArrayList<CountryCasesTotal> confirmedTotal;
    private ArrayList<CountryDeathsTotal> deathTotal;
    private Number[] deaths;

    public CasesAndDeathsView() {
        add(configureCountryNameComboBox());
    }

    @Override
    protected void createGraph(CountryName countryName) {
        DayOneTotalStats dayOneTotalCases = new DayOneTotalStats(countryName, CaseType.CONFIMRED);
        confirmedTotal = dayOneTotalCases.getTotalCases();
        DayOneTotalStats dayOneTotalDeaths = new DayOneTotalStats(countryName, CaseType.DEATHS);
        deathTotal = dayOneTotalDeaths.getTotalDeaths();

        if (isTotalEmpty((ArrayList<T>) confirmedTotal) || isTotalEmpty((ArrayList<T>) deathTotal)) {
            logger.log(Level.FINE, "Data was empty");
            return;
        }

        setCases();
        setDeaths();

        String chartTitle = "Number of deaths confirmed cases since the first death and confirmed case";
        String yAxisName = "Number of deaths & confirmed cases since day 1";
        setChartConfig(chartTitle, yAxisName, "Confirmed cases", confirmedCases);
        setChartConfig(chartTitle, yAxisName, "Deaths", deaths);

        Chart casesAndDeathsChart = getCasesChart();
        add(casesAndDeathsChart);
    }

    private void setCases() {
        String[] confirmedCaseDates = new String[confirmedTotal.size()];
        confirmedCases = new Number[confirmedTotal.size()];

        for (int i = 0; i < confirmedTotal.size(); i++) {
            CountryCasesTotal countryLive = confirmedTotal.get(i);
            confirmedCaseDates[i] = countryLive.getDate();
            confirmedCases[i] = countryLive.getNumberOfCases();
        }
        setCaseDates(confirmedCaseDates);
    }

    private void setDeaths() {
        String[] deathDates = new String[deathTotal.size()];
        deaths = new Number[confirmedTotal.size()];

        for (int i = 0; i < deathTotal.size(); i++) {
            CountryDeathsTotal countryLive = deathTotal.get(i);
            deathDates[i] = countryLive.getDate();
            deaths[i] = countryLive.getNumberOfDeaths();
        }
        setCaseDates(deathDates);
    }
}
