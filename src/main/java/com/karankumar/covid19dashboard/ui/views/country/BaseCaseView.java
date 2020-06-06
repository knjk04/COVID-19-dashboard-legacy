package com.karankumar.covid19dashboard.ui.views.country;

import com.karankumar.covid19dashboard.backend.api.util.CountryName;
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

import static com.karankumar.covid19dashboard.ui.utils.ViewsConst.EMPTY_ERROR_MESSAGE;

public abstract class BaseCaseView<T extends CountryTotal> extends VerticalLayout {
    private Chart casesChart = new Chart(ChartType.AREA);
    private String[] caseDates;

    protected ComboBox<CountryName> configureCountryNameComboBox() {
        ComboBox<CountryName> country = new ComboBox<>("Country");
        country.setItems(CountryName.values());
        country.setRequired(true);
        country.setPlaceholder("Select a country11");
        country.setMinWidth("20%");
        setCountryValueChangeListener(country);

        return country;
    }

    protected void setCountryValueChangeListener(ComboBox<CountryName> country) {
        country.addValueChangeListener(event -> {
            if (event != null && event.isFromClient()) {
                removeExistingChart();
                createGraph(event.getValue());
            }
        });
    }

    /**
     * if the chart is already shown, remove it
     */
    protected void removeExistingChart() {
        if (casesChart.isVisible()) {
            remove(casesChart);
            casesChart = new Chart(ChartType.AREA);
        }
    }

    protected abstract void createGraph(CountryName countryName);

    protected boolean isTotalEmpty(ArrayList<T> casesTotal) {
        if (casesTotal.isEmpty()) {
            Notification notification = new Notification(
                    EMPTY_ERROR_MESSAGE, 5000);
            notification.open();
            return true;
        }
        return false;
    }

    /**
     * This should be called before it is used as the xAxis category. See {@code setChartConfig}
     * @param caseDates The labelled date for each case total (death or confirmed case)
     */
    private void setCaseDates(String[] caseDates) {
        this.caseDates = caseDates;
    }

    protected void setChartConfig(String chartTitle, String yAxisName, String seriesName, Number[] total,
                                  String[] caseDates) {
        Configuration conf = casesChart.getConfiguration();
        conf.setTitle(chartTitle);
        Tooltip tooltip = new Tooltip();
        conf.setTooltip(tooltip);

        setCaseDates(caseDates);
        XAxis xAxis = conf.getxAxis();
        xAxis.setCategories(caseDates);

        YAxis yAxis = conf.getyAxis();
        yAxis.setTitle(yAxisName);

        conf.addSeries(new ListSeries(seriesName, Arrays.asList(total)));
    }

    protected Chart getCasesChart() {
        return casesChart;
    }
}
