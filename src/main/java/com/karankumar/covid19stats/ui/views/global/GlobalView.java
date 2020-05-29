package com.karankumar.covid19stats.ui.views.global;

import com.karankumar.covid19stats.backend.GlobalStats;
import com.karankumar.covid19stats.ui.MainView;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "global", layout = MainView.class)
@PageTitle("Global")
public class GlobalView extends VerticalLayout {
    public GlobalView() {

        com.vaadin.flow.component.html.H4 totalDeaths = new H4();
        totalDeaths.setText("Total deaths");

        com.vaadin.flow.component.html.H4 totalCases = new H4();
        totalCases.setText("Total cases");

        com.vaadin.flow.component.html.H4 totalRecovered = new H4();
        totalRecovered.setText("Total recovered");

        HorizontalLayout summary = new HorizontalLayout(totalDeaths, totalCases, totalRecovered);

        add(summary);

        GlobalStats globalStats = new GlobalStats();

        System.out.println("GlobalView: Total deaths: " + globalStats.getTotalDeaths());
        System.out.println("GlobalView: Total recovered: " + globalStats.getTotalRecovered());
        System.out.println("GlobalView: Total cases: " + globalStats.getTotalCases());

        Board board = new Board();
        Div totalDeathsDiv = createComponent(String.valueOf(globalStats.getTotalDeaths()));
        Div totalRecoveredDiv = createComponent(String.valueOf(globalStats.getTotalRecovered()));
        Div totalCasesDiv = createComponent(String.valueOf(globalStats.getTotalCases()));
        board.addRow(totalDeathsDiv, totalRecoveredDiv, totalCasesDiv);

        add(board);
    }

    private Div createComponent(String text) {
        Div div = new Div();
        div.setText(text);
        return div;
    }
}
