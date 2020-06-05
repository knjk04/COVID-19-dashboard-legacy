package com.karankumar.covid19dashboard.ui.views.global;

import com.karankumar.covid19dashboard.backend.api.util.ApiConst;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.charts.model.PlotOptionsPie;
import com.vaadin.flow.component.charts.model.Tooltip;

import java.util.TreeMap;

class HighestNumberOfChart {
    private Chart mostCasesChart = new Chart(ChartType.PIE);
    private Chart mostDeathsChart = new Chart(ChartType.PIE);

    private enum NUMBER_OF {
        DEATHS,
        CONFIRMED_CASES,
    }

    public Chart createMostCasesChart(TreeMap<Integer, String> mostCases, Integer totalCases) {
        DataSeries series = setDataSeries(mostCases, totalCases);
        setChartConfig(series, NUMBER_OF.CONFIRMED_CASES, "cases");
        return mostCasesChart;
    }

    public Chart createMostDeathsChart(TreeMap<Integer, String> mostDeaths, Integer totalDeaths) {
        DataSeries series = setDataSeries(mostDeaths, totalDeaths);
        setChartConfig(series, NUMBER_OF.DEATHS, "deaths");
        return mostDeathsChart;
    }

    private void setChartConfig(DataSeries series, NUMBER_OF numberOf, String mostOf) {
        Configuration conf;
        int number;
        if (numberOf == NUMBER_OF.DEATHS) {
            number = ApiConst.MOST_DEATHS;
            conf = mostDeathsChart.getConfiguration();
        } else {
            number = ApiConst.MOST_CONFIRMED_CASES;
            conf = mostCasesChart.getConfiguration();
        }

        conf.setTitle("Top " + number + " countries with the most " + mostOf);
        conf.setTooltip(new Tooltip());
        conf.setPlotOptions(setPlotOptionsPie());
        conf.setSeries(series);
    }

    /**
     * @param mostOfStat a TreeMap that represents the top countries with the highest number of a particular statistic
     * @param globalTotal the total number of cases/deaths worldwide
     * @return a DataSeries that contains all of the keys and values in the TreeMap
     */
    private DataSeries setDataSeries(TreeMap<Integer, String> mostOfStat, Integer globalTotal) {
        DataSeries series = new DataSeries();
        int totalInTopCountries = 0;
        for (Integer i : mostOfStat.keySet()) {
            series.add(new DataSeriesItem(mostOfStat.get(i), i));
            totalInTopCountries += i;
        }
        series.add(new DataSeriesItem("Other", (globalTotal - totalInTopCountries)));
        return series;
    }

    private PlotOptionsPie setPlotOptionsPie() {
        PlotOptionsPie plotOptions = new PlotOptionsPie();
        plotOptions.setAllowPointSelect(true);
        return plotOptions;
    }
}
