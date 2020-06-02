package com.karankumar.covid19dashboard.ui.views.global;

import com.karankumar.covid19dashboard.backend.api.ApiConst;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;

import java.util.TreeMap;

public class MostChart {
    private Chart mostCasesChart = new Chart(ChartType.PIE);
    private Chart mostDeathsChart = new Chart(ChartType.PIE);

    private enum MOST {
        DEATHS,
        CONFIRMED_CASES,
    }

    public Chart createMostCasesChart(TreeMap<Integer, String> mostCases) {
        configureMostCasesChart(mostCases);
        return mostCasesChart;
    }

    public Chart createMostDeathsChart(TreeMap<Integer, String> mostDeaths) {
        configureMostDeathsChart(mostDeaths);
        return mostDeathsChart;
    }

    private void configureMostCasesChart(TreeMap<Integer, String> mostCases) {
        PlotOptionsPie plotOptions = setPlotOptionsPie();
        DataSeries series = setDataSeries(mostCases);
        setChartConfig(plotOptions, series, MOST.CONFIRMED_CASES, "cases");
    }

    private void configureMostDeathsChart(TreeMap<Integer, String> mostDeaths) {
        PlotOptionsPie plotOptions = setPlotOptionsPie();
        DataSeries series = setDataSeries(mostDeaths);
        setChartConfig(plotOptions, series, MOST.DEATHS, "deaths");
    }

    private void setChartConfig(PlotOptionsPie plotOptions, DataSeries series, MOST most, String mostOf) {
        Configuration conf;
        int number;
        if (most == MOST.DEATHS) {
            number = ApiConst.MOST_DEATHS;
            conf = mostDeathsChart.getConfiguration();
        } else {
            number = ApiConst.MOST_CONFIRMED_CASES;
            conf = mostCasesChart.getConfiguration();
        }

        conf.setTitle("Top " + number + " countries with the most " + mostOf);
        conf.setTooltip(new Tooltip());
        conf.setPlotOptions(plotOptions);
        conf.setSeries(series);
    }

    /**
     * @param mostOfStat a TreeMap that represents the top countries with the highest number of a particular statistic
     * @return a DataSeries that contains all of the keys and values in the TreeMap
     */
    private DataSeries setDataSeries(TreeMap<Integer, String> mostOfStat) {
        DataSeries series = new DataSeries();
        for (Integer i : mostOfStat.keySet()) {
            series.add(new DataSeriesItem(mostOfStat.get(i), i));
        }
        return series;
    }

    private PlotOptionsPie setPlotOptionsPie() {
        PlotOptionsPie plotOptions = new PlotOptionsPie();
        plotOptions.setAllowPointSelect(true);
        return plotOptions;
    }
}
