package com.karankumar.covid19dashboard.ui.views.country;

import com.karankumar.covid19dashboard.backend.api.dayone.CaseType;
import com.karankumar.covid19dashboard.backend.api.dayone.DayOneTotalStats;
import com.karankumar.covid19dashboard.backend.api.util.CountryName;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryCasesTotal;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryTotal;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.Tooltip;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.charts.model.YAxis;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.karankumar.covid19dashboard.ui.utils.ViewsConst.EMPTY_ERROR_MESSAGE;

public class CaseComparisonView<T extends CountryTotal> extends VerticalLayout {
    private ArrayList<CountryCasesTotal> casesTotal;
    private Chart casesChart = new Chart(ChartType.AREA);
    private static final Logger logger = Logger.getLogger(CountryView.class.getName());
    private String[] caseDates;
    private Number[] cases;

    public CaseComparisonView() {
        ComboBox<CountryName> selectCountry = configureCountryNameComboBox();
        selectCountry.setMinWidth("20%");
        Button clear = new Button("Reset", e -> removeExistingChart());

        HorizontalLayout horizontalLayout = new HorizontalLayout(selectCountry, clear);
        horizontalLayout.setAlignItems(Alignment.END);
        horizontalLayout.setSizeFull();

        add(horizontalLayout);
    }

    private ComboBox<CountryName> configureCountryNameComboBox() {
        ComboBox<CountryName> country = new ComboBox<>("Country");
        country.setItems(CountryName.values());
        country.setRequired(true);
        country.setPlaceholder("Select a country");
        country.setMinWidth("20%");

        country.addValueChangeListener(event -> {
            if (event != null && event.isFromClient()) {
                createCasesGraph(event.getValue());
            }
        });
        return country;
    }

    private void createCasesGraph(CountryName countryName) {
        DayOneTotalStats dayOneTotalCases = new DayOneTotalStats(countryName, CaseType.CONFIMRED);
        casesTotal = dayOneTotalCases.getTotalCases();

        if (isTotalEmpty((ArrayList<T>) casesTotal)) {
            logger.log(Level.FINE, "Data was empty");
            return;
        }

        setCases();

        String chartTitle = "Number of confirmed cases since the first confirmed case";
        String yAxisName = "Number of confirmed cases";
        setChartConfig(chartTitle, yAxisName, countryName.toString(), cases);
        add(casesChart);
    }

    private void removeExistingChart() {
        if (casesChart.isVisible()) {
            remove(casesChart);
            casesChart = new Chart(ChartType.AREA);
        }
    }

    private boolean isTotalEmpty(ArrayList<T> casesTotal) {
        if (casesTotal.isEmpty()) {
            Notification notification = new Notification(
                    EMPTY_ERROR_MESSAGE, 5000);
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

    private void setChartConfig(String chartTitle, String yAxisName, String seriesName, Number[] total) {
        Configuration conf = casesChart.getConfiguration();
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
