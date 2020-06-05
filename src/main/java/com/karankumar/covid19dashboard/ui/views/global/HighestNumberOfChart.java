package com.karankumar.covid19dashboard.ui.views.global;

import com.karankumar.covid19dashboard.ui.utils.ViewsConst;
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
        setChartConfig(series, NUMBER_OF.CONFIRMED_CASES);
        return mostCasesChart;
    }

    public Chart createMostDeathsChart(TreeMap<Integer, String> mostDeaths, Integer totalDeaths) {
        DataSeries series = setDataSeries(mostDeaths, totalDeaths);
        setChartConfig(series, NUMBER_OF.DEATHS);
        return mostDeathsChart;
    }

    private void setChartConfig(DataSeries series, NUMBER_OF numberOf) {
        Configuration conf;
        Tooltip tooltip =  new Tooltip();
        String caseType;
        if (numberOf == NUMBER_OF.DEATHS) {
            conf = mostDeathsChart.getConfiguration();
            tooltip.setValueSuffix(ViewsConst.DEATHS_TOOLTIP_SUFFIX);
            caseType = "confirmed cases";
        } else {
            conf = mostCasesChart.getConfiguration();
            tooltip.setValueSuffix(ViewsConst.CASES_TOOLTIP_SUFFIX);
            caseType = "deaths";
        }
        conf.setTitle("Countries with the most " + caseType);
        conf.setTooltip(tooltip);
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
