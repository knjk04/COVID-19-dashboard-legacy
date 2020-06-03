package com.karankumar.covid19dashboard.ui.views.global;

import com.karankumar.covid19dashboard.backend.api.util.ApiConst;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;

import java.util.TreeMap;

class HighestNumberOfChart {
    private Chart mostCasesChart = new Chart(ChartType.PIE);
    private Chart mostDeathsChart = new Chart(ChartType.PIE);

    private enum NUMBER_OF {
        DEATHS,
        CONFIRMED_CASES,
    }

    public Chart createMostCasesChart(TreeMap<Integer, String> mostCases) {
        DataSeries series = setDataSeries(mostCases);
        setChartConfig(series, NUMBER_OF.CONFIRMED_CASES, "cases");
        return mostCasesChart;
    }

    public Chart createMostDeathsChart(TreeMap<Integer, String> mostDeaths) {
        DataSeries series = setDataSeries(mostDeaths);
        setChartConfig(series, NUMBER_OF.DEATHS, "deaths");
        return mostDeathsChart;
    }

    private void setChartConfig(DataSeries series, NUMBER_OF most, String mostOf) {
        Configuration conf;
        int number;
        if (most == NUMBER_OF.DEATHS) {
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
