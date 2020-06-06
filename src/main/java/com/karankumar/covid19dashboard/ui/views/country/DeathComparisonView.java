package com.karankumar.covid19dashboard.ui.views.country;

import com.karankumar.covid19dashboard.backend.api.dayone.CaseType;
import com.karankumar.covid19dashboard.backend.api.dayone.DayOneTotalStats;
import com.karankumar.covid19dashboard.backend.api.util.CountryName;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryDeathsTotal;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryTotal;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeathComparisonView<T extends CountryTotal> extends BaseCaseView<T> {
    private ArrayList<CountryDeathsTotal> totalDeaths;
    private static final Logger logger = Logger.getLogger(CountryView.class.getName());
    private Number[] deaths;
    private String[] deathDates;

    public DeathComparisonView() {
        ComboBox<CountryName> selectCountry = configureCountryNameComboBox();
        Button clear = new Button("Reset", e -> removeExistingChart());

        HorizontalLayout horizontalLayout = new HorizontalLayout(selectCountry, clear);
        horizontalLayout.setAlignItems(Alignment.END);
        horizontalLayout.setSizeFull();

        add(horizontalLayout);
    }

    @Override
    protected void setCountryValueChangeListener(ComboBox<CountryName> country) {
        country.addValueChangeListener(event -> {
            if (event != null && event.isFromClient()) {
                createGraph(event.getValue());
            }
        });
    }

    @Override
    protected void createGraph(CountryName countryName) {
        DayOneTotalStats totalDeathsSinceDayOne = new DayOneTotalStats(countryName, CaseType.DEATHS);
        totalDeaths = totalDeathsSinceDayOne.getTotalDeaths();

        if (isTotalEmpty((ArrayList<T>) totalDeaths)) {
            logger.log(Level.FINE, "Data was empty");
            return;
        }

        setDeaths(); // this must be called before calling setChartConfig()
        String chartTitle = "Number of deaths since the first death";
        String yAxisName = "Number of deaths";
        setChartConfig(chartTitle, yAxisName, countryName.toString(), deaths, deathDates);

        Chart deathChart = getCasesChart();
        add(deathChart);
    }

    private void setDeaths() {
        deathDates = new String[totalDeaths.size()];
        deaths = new Number[totalDeaths.size()];

        for (int i = 0; i < totalDeaths.size(); i++) {
            CountryDeathsTotal countryLive = totalDeaths.get(i);
            deathDates[i] = countryLive.getDate();
            deaths[i] = countryLive.getNumberOfDeaths();
        }
    }
}
