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
        int totalDeaths = globalStats.getTotalDeaths();
        int totalRecovered = globalStats.getTotalRecovered();
        int totalCases = globalStats.getTotalCases();

        System.out.println("GlobalView: Total deaths: " + totalDeaths);
        System.out.println("GlobalView: Total recovered: " + totalRecovered);
        System.out.println("GlobalView: Total cases: " + totalCases);

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(true);

        Board board = new Board();
        Div totalDeathsDiv = createComponent("Total deaths: " + numberFormat.format(totalDeaths));
        Div totalRecoveredDiv = createComponent("Total recovered: " + numberFormat.format(totalRecovered));
        Div totalCasesDiv = createComponent("Total confirmed cases: " + numberFormat.format(totalCases));
        board.addRow(totalDeathsDiv, totalRecoveredDiv, totalCasesDiv);

        add(board);
    }

    private Div createComponent(String text) {
        Div div = new Div();
        div.setText(text);
        return div;
    }
}
