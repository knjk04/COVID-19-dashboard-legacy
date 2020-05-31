package com.karankumar.covid19dashboard.ui.views.global;

import com.karankumar.covid19dashboard.backend.api.ApiStats;
import com.karankumar.covid19dashboard.ui.MainView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.text.NumberFormat;

@Route(value = "global", layout = MainView.class)
@PageTitle("COVID-19: Global statistics")
public class GlobalView extends VerticalLayout {
    public GlobalView() {
        ApiStats globalStats = new ApiStats();
        Integer totalDeaths = globalStats.getTotalDeaths();
        Integer totalRecovered = globalStats.getTotalRecovered();
        Integer totalCases = globalStats.getTotalCases();

        System.out.println("GlobalView: Total deaths: " + totalDeaths);
        System.out.println("GlobalView: Total recovered: " + totalRecovered);
        System.out.println("GlobalView: Total cases: " + totalCases);

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(true);

        Board board = new Board();

        Span deathCounter = new Span("test1");
        deathCounter.addClassNames("counter", "deathCounter");
        Span recoveredCounter = new Span("test2");
        recoveredCounter.addClassNames("counter", "recoveredCounter");
        Span casesCounter = new Span("test3");
        casesCounter.addClassNames("counter", "casesCounter");

        Div totalDeathsDiv = new Div();
        if (totalDeaths != null) {
            deathCounter.setText(numberFormat.format(totalDeaths));
            H4 deathsH4 = new H4("Total deaths");
            totalDeathsDiv.add(deathsH4);
        }

        Div totalRecoveredDiv = new Div();
        if (totalRecovered != null) {
            recoveredCounter.setText(numberFormat.format(totalRecovered));
            H4 totalRecoveredH4 = new H4("Total recovered");
            totalRecoveredDiv.add(totalRecoveredH4);
        }

        Div totalCasesDiv = new Div();
        if (totalCases != null) {
            casesCounter.setText(numberFormat.format(totalCases));
            H4 totalCasesH4 = new H4("Total confirmed cases");
            totalCasesDiv.add(totalCasesH4);
        }

        board.addRow(deathCounter, recoveredCounter, casesCounter);
        board.addRow(totalDeathsDiv, totalRecoveredDiv, totalCasesDiv);

        add(board);

        add(new Anchor("https://covid19api.com/", "Source: COVID-19 API"));
    }
}
