package com.karankumar.covid19dashboard.ui.views.country.comparison;

import com.karankumar.covid19dashboard.backend.api.dayone.CaseType;
import com.karankumar.covid19dashboard.backend.api.dayone.DayOneTotalStats;
import com.karankumar.covid19dashboard.backend.api.util.CountryName;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryCasesTotal;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryTotal;
import com.karankumar.covid19dashboard.ui.views.country.CountryView;
import com.vaadin.flow.component.charts.Chart;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfirmedCaseComparisonView<T extends CountryTotal> extends ComparisonView<T> {
    private ArrayList<CountryCasesTotal> casesTotal;
    private static final Logger logger = Logger.getLogger(CountryView.class.getName());
    private String[] caseDates;
    private Number[] cases;

    public ConfirmedCaseComparisonView() {
        super(CaseType.CONFIMRED);
        add(createResetAndCountryComboBox());
    }

    @Override
    protected void createGraph(CountryName countryName) {
        DayOneTotalStats dayOneTotalCases = new DayOneTotalStats(countryName, CaseType.CONFIMRED);
        casesTotal = dayOneTotalCases.getTotalConfirmedCases();

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

    protected void setCases() {
        caseDates = new String[casesTotal.size()];
        cases = new Number[casesTotal.size()];

        for (int i = 0; i < casesTotal.size(); i++) {
            CountryCasesTotal countryLive = casesTotal.get(i);
            caseDates[i] = countryLive.getDate();
            cases[i] = countryLive.getNumberOfCases();
        }
    }
}
