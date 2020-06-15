package com.karankumar.covid19dashboard.ui.views.global;

import com.karankumar.covid19dashboard.backend.api.summary.SummaryStats;
import com.karankumar.covid19dashboard.backend.domain.CountrySummary;
import com.karankumar.covid19dashboard.ui.MainView;
import com.karankumar.covid19dashboard.ui.component.DashboardFooter;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Route(value = "global", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("COVID-19: Global statistics")
public class GlobalView extends VerticalLayout {
    private final Span deathCounter;
    private final Span recoveredCounter;
    private final Span casesCounter;

    private static final Logger logger = Logger.getLogger(GlobalView.class.getName());

    private enum TotalType {
        DEATHS,
        RECOVERED,
        CASES
    }

    public GlobalView() {
        SummaryStats globalStats = new SummaryStats();
        Integer totalDeaths = globalStats.getTotalDeaths();
        Integer totalRecovered = globalStats.getTotalRecovered();
        Integer totalCases = globalStats.getTotalConfirmedCases();

        logger.log(Level.INFO, "GlobalView: Total deaths: " + totalDeaths);
        logger.log(Level.INFO, "GlobalView: Total recovered: " + totalRecovered);
        logger.log(Level.INFO, "GlobalView: Total cases: " + totalCases);

        notifyIfNoStatsShown(totalDeaths, totalRecovered, totalCases);

        Board board = new Board();

        String counterDigit = "counterDigit";
        String counterWrapper = "counterWrapper";
        deathCounter = new Span();
        deathCounter.addClassNames(counterDigit, "deathCounterDigit", counterWrapper);
        recoveredCounter = new Span();
        recoveredCounter.addClassNames(counterDigit, "recoveredCounterDigit", counterWrapper);
        casesCounter = new Span();
        casesCounter.addClassNames(counterDigit, "casesCounterDigit", counterWrapper);

        H4 deathsH4 = new H4("Total deaths");
        H4 totalRecoveredH4 = new H4("Total recovered");
        H4 totalCasesH4 = new H4("Total confirmed cases");

        ArrayList<H4> labels = new ArrayList<>(4);
        labels.add(deathsH4);
        labels.add(totalRecoveredH4);
        labels.add(totalCasesH4);
        labels.forEach(h4 -> h4.addClassName(counterWrapper));

        Div totalDeathsDiv = addDivLabel(deathsH4);
        setCounter(totalDeaths, TotalType.DEATHS);
        Div totalRecoveredDiv = addDivLabel(totalRecoveredH4);
        setCounter(totalRecovered, TotalType.RECOVERED);
        Div totalCasesDiv = addDivLabel(totalCasesH4);
        setCounter(totalCases, TotalType.CASES);

        board.addRow(deathCounter, recoveredCounter, casesCounter);
        board.addRow(totalDeathsDiv, totalRecoveredDiv, totalCasesDiv);

        add(board);

        ListDataProvider<CountrySummary> dataProvider = new ListDataProvider<>(globalStats.getAllCountriesSummary());

        ComboBox<String> countryFilter = new ComboBox<>("Filter by country");
        countryFilter.setPlaceholder("Select a country");
        countryFilter.setClearButtonVisible(true);
        countryFilter.setMinWidth("12%");
        List<String> countryName = dataProvider.getItems()
                .stream()
                .map(CountrySummary::getCountryName)
                .collect(Collectors.toList());
        countryFilter.setItems(countryName);
        countryFilter.addValueChangeListener(event -> filterByCountry(countryFilter.getValue(), dataProvider));
        add(countryFilter);

        Grid<CountrySummary> grid = new Grid<>(CountrySummary.class);
        grid.setDataProvider(dataProvider);
        add(grid);

        add(new HtmlComponent("br"));

        HighestNumberOfChart mostChart = new HighestNumberOfChart();
        Chart mostDeathsChart = mostChart.createMostDeathsChart(globalStats.getMostDeaths(), totalDeaths);
        Chart mostCasesChart = mostChart.createMostCasesChart(globalStats.getMostCases(), totalCases);

        HorizontalLayout charts = new HorizontalLayout(mostDeathsChart, mostCasesChart);
        charts.setSizeFull();
        add(charts);

        String lastUpdatedOn = "Last updated on ";
        if (globalStats.getDate() != null && !globalStats.getDate().isEmpty()) {
            lastUpdatedOn += globalStats.getDate();
            if (globalStats.getTime() != null && !globalStats.getTime().isEmpty()) {
                lastUpdatedOn += " at " + globalStats.getTime();
                add(new Text(lastUpdatedOn));
            }
        }

        add(new DashboardFooter());
    }

    private void notifyIfNoStatsShown(Integer totalDeaths, Integer totalRecovered, Integer totalCases) {
        if (totalDeaths == null && totalRecovered == null && totalCases == null) {
            Notification notification = new Notification(
                    "Please refresh the page",
                    5000
            );
            notification.open();
        }
    }

    /**
     * Case-insensitive filter
     * @param dataProvider the grid's {@code DataProvider}
     */
    private void filterByCountry(String query, ListDataProvider<CountrySummary> dataProvider) {
        dataProvider.clearFilters();
        if (query != null && !query.isEmpty()) {
            dataProvider.addFilter(countrySummary -> query.equals(countrySummary.getCountryName()));
        }
    }

    private Div addDivLabel(H4 label) {
        Div div = new Div();
        div.add(label);
        return div;
    }

    private void setCounter(Integer total, TotalType type) {
        if (total != null) {
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setGroupingUsed(true);
            String formattedTotal = numberFormat.format(total);
            switch (type) {
                case DEATHS:
                    deathCounter.setText(formattedTotal);
                    break;
                case RECOVERED:
                    recoveredCounter.setText(formattedTotal);
                    break;
                case CASES:
                    casesCounter.setText(formattedTotal);
                    break;
            }
        }
    }
}
