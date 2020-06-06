package com.karankumar.covid19dashboard.ui.views.country;

import com.karankumar.covid19dashboard.backend.api.dayone.CaseType;
import com.karankumar.covid19dashboard.backend.api.dayone.DayOneTotalStats;
import com.karankumar.covid19dashboard.backend.api.util.CountryName;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryCasesTotal;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryTotal;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CaseComparisonView<T extends CountryTotal> extends BaseCaseView<T> {
    private ArrayList<CountryCasesTotal> casesTotal;
    private static final Logger logger = Logger.getLogger(CountryView.class.getName());
    private String[] caseDates;
    private Number[] cases;

    public CaseComparisonView() {
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
        DayOneTotalStats dayOneTotalCases = new DayOneTotalStats(countryName, CaseType.CONFIMRED);
        casesTotal = dayOneTotalCases.getTotalCases();

        if (isTotalEmpty((ArrayList<T>) casesTotal)) {
            logger.log(Level.FINE, "Data was empty");
            return;
        }

        setCases(); // this must be called before calling setChartConfig()

        String chartTitle = "Number of confirmed cases since the first confirmed case";
        String yAxisName = "Number of confirmed cases";
        setChartConfig(chartTitle, yAxisName, countryName.toString(), cases, caseDates);

        Chart confirmedCasesChart = getCasesChart();
        add(confirmedCasesChart);
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
}
