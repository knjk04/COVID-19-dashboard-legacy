package com.karankumar.covid19dashboard.ui.views.global;

import com.karankumar.covid19dashboard.backend.global.GlobalStats;
import com.karankumar.covid19dashboard.ui.MainView;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.text.NumberFormat;

@Route(value = "global", layout = MainView.class)
@PageTitle("COVID-19: Global statistics")
public class GlobalView extends VerticalLayout {
    public GlobalView() {
        GlobalStats globalStats = new GlobalStats();
        Integer totalDeaths = globalStats.getTotalDeaths();
        Integer totalRecovered = globalStats.getTotalRecovered();
        Integer totalCases = globalStats.getTotalCases();

        System.out.println("GlobalView: Total deaths: " + totalDeaths);
        System.out.println("GlobalView: Total recovered: " + totalRecovered);
        System.out.println("GlobalView: Total cases: " + totalCases);

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(true);

        Board board = new Board();

        Div totalDeathsDiv = new Div();
        if (totalDeaths != null) {
            H4 deathsh4 = new H4("Total deaths: " + numberFormat.format(totalDeaths));
            totalDeathsDiv.add(deathsh4);
        }

        Div totalRecoveredDiv = new Div();
        if (totalRecovered != null) {
            H4 totalRecoveredH4 = new H4("Total recovered: " + numberFormat.format(totalRecovered));
            totalRecoveredDiv.add(totalRecoveredH4);
        }

        Div totalCasesDiv = new Div();
        if (totalCases != null) {
            H4 totalCasesH4 = new H4("Total confirmed cases: " + numberFormat.format(totalCases));
            totalCasesDiv.add(totalCasesH4);
        }

        board.addRow(totalDeathsDiv, totalRecoveredDiv, totalCasesDiv);

        add(board);
    }

    private Div createComponent(String text) {
        Div div = new Div();
        div.setText(text);
        return div;
    }
}
