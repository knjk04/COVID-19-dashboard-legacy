package com.karankumar.covid19dashboard.ui.views.country;

import com.karankumar.covid19dashboard.backend.api.dayone.CaseType;
import com.karankumar.covid19dashboard.backend.api.dayone.DayOneTotalStats;
import com.karankumar.covid19dashboard.backend.api.util.CountryName;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryCasesTotal;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryDeathsTotal;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryTotal;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.Tooltip;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.charts.model.YAxis;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CasesAndDeathsView <T extends CountryTotal>  extends VerticalLayout {
    private Chart casesAndDeathsChart = new Chart(ChartType.AREA);
    private static final Logger logger = Logger.getLogger(CountryView.class.getName());

    private String[] caseDates;
    private Number[] cases;
    private ArrayList<CountryCasesTotal> casesTotal;
    private ArrayList<CountryDeathsTotal> deathTotal;
    private Number[] deaths;
    private String[] deathDates;

    public CasesAndDeathsView() {
        ComboBox<CountryName> country = new ComboBox<>("Country");
        country.setItems(CountryName.values());
        country.setRequired(true);
        country.setPlaceholder("Select a country");
        country.setMinWidth("20%");
        add(country);

        country.addValueChangeListener(event -> {
            if (event != null && event.isFromClient()) {
                removeExistingChart();
                createCasesAndDeathsGraph(event.getValue());
            }
        });
    }

    // if the chart is already shown, remove it
    private void removeExistingChart() {
        if (casesAndDeathsChart.isVisible()) {
            remove(casesAndDeathsChart);
            casesAndDeathsChart = new Chart(ChartType.AREA);
        }
    }

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
        add(casesAndDeathsChart);
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
            deathDates[i] = countryLive.getDate();
            deaths[i] = countryLive.getNumberOfDeaths();
        }
    }

    private void setChartConfig(String chartTitle, String yAxisName, String seriesName, Number[] total) {
        Configuration conf = casesAndDeathsChart.getConfiguration();
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
