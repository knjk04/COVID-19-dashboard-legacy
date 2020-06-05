package com.karankumar.covid19dashboard.ui.views.country;

import com.karankumar.covid19dashboard.backend.api.dayone.CaseType;
import com.karankumar.covid19dashboard.backend.api.dayone.DayOneTotalStats;
import com.karankumar.covid19dashboard.backend.api.util.CountryName;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryCasesTotal;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryDeathsTotal;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryTotal;
import com.karankumar.covid19dashboard.ui.MainView;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.Tooltip;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.charts.model.YAxis;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

@Route(value = "country", layout = MainView.class)
@PageTitle("COVID-19: Country statistics")
public class CountryView <T extends CountryTotal> extends VerticalLayout {
    private Chart chart = new Chart(ChartType.AREA);
    private static final Logger logger = Logger.getLogger(DayOneTotalStats.class.getName());

    private String[] caseDates;
    private Number[] cases;
    private ArrayList<CountryCasesTotal> casesTotal;
    private ArrayList<CountryDeathsTotal> deathTotal;
    private Number[] deaths;
    private String[] deathDates;

    public CountryView() {
        ComboBox<CountryName> country = new ComboBox<>("Country");
        country.setItems(CountryName.values());
        country.setRequired(true);
        country.setPlaceholder("Select a country");
        country.setMinWidth("20%");

//        Button clearButton = new Button("Reset", event -> removeExistingChart());
//        HorizontalLayout horizontalLayout = new HorizontalLayout(country, clearButton);

        HorizontalLayout horizontalLayout = new HorizontalLayout(country);
        horizontalLayout.setAlignItems(Alignment.END);
        horizontalLayout.setMinWidth("60%");
        add(horizontalLayout);

        country.addValueChangeListener(event -> {
            if (event != null && event.isFromClient()) {
//                createCasesGraph(event.getValue());
//                createDeathsGraph(event.getValue());

                removeExistingChart();
                createCasesAndDeathsGraph(event.getValue());
            }
        });

        add(new Anchor("https://covid19api.com/", "Source: COVID-19 API"));
    }

    // if the chart is already shown, remove it
    private void removeExistingChart() {
        if (chart.isVisible()) {
            remove(chart);
            chart = new Chart(ChartType.AREA);
        }
    }

//    private void createCasesGraph(CountryName countryName) {
//        DayOneTotalStats dayOneTotal = new DayOneTotalStats(countryName, CaseType.CONFIMRED);
//        ArrayList<CountryCasesTotal> countryTotal = dayOneTotal.getTotalCases();
//
//        if (countryTotal.isEmpty()) {
//            logger.log(Level.FINE, "Data was empty");
//            Notification notification = new Notification(
//                    "Cannot retrieve the data for this country, please try a different country", 5000);
//            notification.open();
//            return;
//        }
//
//        Configuration conf = chart.getConfiguration();
////        conf.setTitle("Number of confirmed cases since the first confirmed case");
//        Tooltip tooltip = new Tooltip();
//        tooltip.setValueSuffix(" cases");
//        conf.setTooltip(tooltip);
//
//        String[] dates = new String[countryTotal.size()];
//        Number[] cases = new Number[countryTotal.size()];
//
//        for (int i = 0; i < countryTotal.size(); i++) {
//            CountryCasesTotal countryLive = countryTotal.get(i);
//            dates[i] = countryLive.getDate();
//            cases[i] = countryLive.getNumberOfCases();
//        }
//
//        XAxis xAxis = conf.getxAxis();
//        xAxis.setCategories(dates);
//
//        YAxis yAxis = conf.getyAxis();
//        yAxis.setTitle("Number of confirmed cases");
//
//        conf.addSeries(new ListSeries("Confirmed cases", Arrays.asList(cases)));
//
//        add(chart);
//    }

//    private void createDeathsGraph(CountryName countryName) {
//        DayOneTotalStats dayOneTotal = new DayOneTotalStats(countryName, CaseType.DEATHS);
//        ArrayList<CountryDeathsTotal> countriesTotal = dayOneTotal.getTotalDeaths();
//
//        if (countriesTotal.isEmpty()) {
//            logger.log(Level.FINE, "Data was empty");
//            Notification notification = new Notification(
//                    "Cannot retrieve the data for this country, please try a different country", 5000);
//            notification.open();
//            return;
//        }
//
//        Configuration conf = chart.getConfiguration();
////        conf.setTitle("Number of deaths since the first COVID-19 death");
//        Tooltip tooltip = new Tooltip();
//        conf.setTooltip(tooltip);
//
//        String[] dates = new String[countriesTotal.size()];
//        Number[] deaths = new Number[countriesTotal.size()];
//
//        for (int i = 0; i < countriesTotal.size(); i++) {
//            CountryDeathsTotal countryTotal = countriesTotal.get(i);
//            dates[i] = countryTotal.getDate();
//            deaths[i] = countryTotal.getNumberOfDeaths();
//        }
//
//        XAxis xAxis = conf.getxAxis();
//        xAxis.setCategories(dates);
//
//        YAxis yAxis = conf.getyAxis();
//        yAxis.setTitle("Number of confirmed cases");
//
//        conf.addSeries(new ListSeries("Deaths", Arrays.asList(deaths)));
//
//        add(chart);
//    }

    private void createCasesAndDeathsGraph(CountryName countryName) {
        DayOneTotalStats dayOneTotalCases = new DayOneTotalStats(countryName, CaseType.CONFIMRED);
        casesTotal = dayOneTotalCases.getTotalCases();
        DayOneTotalStats dayOneTotalDeaths = new DayOneTotalStats(countryName, CaseType.DEATHS);
        deathTotal = dayOneTotalDeaths.getTotalDeaths();

        if (isTotalEmpty((ArrayList<T>) casesTotal) || isTotalEmpty((ArrayList<T>) deathTotal)) {
            return;
        }

        setCases();
        setDeaths();

        String chartTitle = "Number of deaths confirmed cases since the first death and confirmed case";
        String yAxisName = "Number of deaths & confirmed cases since day 1";
        setChartConfig(chartTitle, yAxisName, "Confirmed cases", cases);
        setChartConfig(chartTitle, yAxisName, "Deaths", deaths);
        add(chart);
    }

    private boolean isTotalEmpty(ArrayList<T> casesTotal) {
        if (casesTotal.isEmpty()) {
            logger.log(Level.FINE, "Data was empty");
            Notification notification = new Notification(
                    "Cannot retrieve the data for this country, please try a different country", 5000);
            notification.open();
            return true;
        }
        return false;
    }

    private void setCases() {
        caseDates = new String[casesTotal.size()];
        cases = new Number[casesTotal.size()];

        for (int i = 0; i < casesTotal.size(); i++) {
            CountryCasesTotal countryLive = casesTotal.get(i);
            caseDates[i] = countryLive.getDate();
            cases[i] = countryLive.getNumberOfCases();
        }
    }

    private void setDeaths() {
        deathDates = new String[deathTotal.size()];
        deaths = new Number[casesTotal.size()];

        for (int i = 0; i < deathTotal.size(); i++) {
            CountryDeathsTotal countryLive = deathTotal.get(i);
            caseDates[i] = countryLive.getDate();
            deaths[i] = countryLive.getNumberOfDeaths();
        }
    }

    private void setChartConfig(String chartTitle, String yAxisName, String seriesName, Number[] total) {
        Configuration conf = chart.getConfiguration();
        conf.setTitle(chartTitle);
        Tooltip tooltip = new Tooltip();
        conf.setTooltip(tooltip);

        XAxis xAxis = conf.getxAxis();
        xAxis.setCategories(caseDates);

        YAxis yAxis = conf.getyAxis();
        yAxis.setTitle(yAxisName);

        conf.addSeries(new ListSeries(seriesName, Arrays.asList(total)));
    }
}
