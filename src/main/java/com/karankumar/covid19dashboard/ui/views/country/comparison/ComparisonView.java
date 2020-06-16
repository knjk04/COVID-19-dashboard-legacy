package com.karankumar.covid19dashboard.ui.views.country.comparison;

import com.karankumar.covid19dashboard.backend.api.dayone.CaseType;
import com.karankumar.covid19dashboard.backend.api.dayone.DayOneTotalStats;
import com.karankumar.covid19dashboard.backend.api.util.CountryName;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryTotal;
import com.karankumar.covid19dashboard.ui.views.country.BaseCaseView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ComparisonView<T extends CountryTotal> extends BaseCaseView<T> {
    private final CaseType caseType;
    private Number[] cases;
    private String[] caseDates;
    private ArrayList<CountryTotal> totalCases;

    private static final Logger logger = Logger.getLogger(ComparisonView.class.getName());

    public ComparisonView(CaseType caseType) {
        this.caseType = caseType;
    }

    protected void createGraph(CountryName countryName, CaseType caseType) {
        DayOneTotalStats totalCasesSinceDayOne;
        String chartTitle = null;
        String yAxisName = null;
        if (caseType == CaseType.DEATHS) {
            totalCasesSinceDayOne = new DayOneTotalStats(countryName, CaseType.DEATHS);
            totalCases = totalCasesSinceDayOne.getTotalDeaths();
            yAxisName = "Number of deaths";
            chartTitle = "Number of deaths since the first death";
        } else if (caseType == CaseType.CONFIMRED) {
            totalCasesSinceDayOne = new DayOneTotalStats(countryName, CaseType.CONFIMRED);
            totalCases = totalCasesSinceDayOne.getTotalConfirmedCases();
            yAxisName = "Number of confirmed cases";
            chartTitle = "Number of confirmed cases since the first case";
        }

        if (isTotalEmpty((ArrayList<T>) totalCases, countryName)) {
            logger.log(Level.FINE, "Data was empty");
            return;
        }

        setCases(); // this must be called before setChartConfig()
        setChartConfig(chartTitle, yAxisName, countryName.toString(), getCases(), getCaseDates());
        Chart deathChart = getCasesChart();
        add(deathChart);
    }

    @Override
    protected void setCountryValueChangeListener(ComboBox<CountryName> country) {
        country.addValueChangeListener(event -> {
            if (event != null && event.isFromClient()) {
                createGraph(event.getValue(), caseType);
            }
        });
    }

    @Override
    protected void createGraph(CountryName countryName) {
    }

    protected HorizontalLayout createResetAndCountryComboBox() {
        ComboBox<CountryName> selectCountry = configureCountryNameComboBox();
        Button clear = new Button("Reset", e -> removeExistingChart());

        HorizontalLayout horizontalLayout = new HorizontalLayout(selectCountry, clear);
        horizontalLayout.setAlignItems(Alignment.END);
        horizontalLayout.setSizeFull();
        return horizontalLayout;
    }

    protected void setCases() {
        caseDates = new String[totalCases.size()];
        cases = new Number[totalCases.size()];

        for (int i = 0; i < totalCases.size(); i++) {
            CountryTotal countryLive = null;
            if (caseType == CaseType.DEATHS) {
                countryLive = totalCases.get(i);
            } else if (caseType == CaseType.CONFIMRED) {
                countryLive = totalCases.get(i);
            }
            cases[i] = countryLive.getNumberOfCases();
            caseDates[i] = countryLive.getDate();
        }
    }

    protected String[] getCaseDates() {
        return caseDates;
    }

    public Number[] getCases() {
        return cases;
    }
}
