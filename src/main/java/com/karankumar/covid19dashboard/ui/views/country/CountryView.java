package com.karankumar.covid19dashboard.ui.views.country;

import com.karankumar.covid19dashboard.ui.MainView;
import com.karankumar.covid19dashboard.ui.component.DashboardFooter;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "country", layout = MainView.class)
@PageTitle("COVID-19: Country statistics")
public class CountryView extends VerticalLayout {

    public CountryView() {
        Accordion accordion = new Accordion();
        accordion.setWidthFull();

        VerticalLayout casesAndDeathsView = new CasesAndDeathsView();
        accordion.add("Total confirmed cases and deaths for one country", casesAndDeathsView);

        VerticalLayout caseComparison = new CaseComparisonView();
        accordion.add("Compare total confirmed cases", caseComparison);

        VerticalLayout deathComparison = new DeathComparisonView();
        accordion.add("Compare total COVID-19 deaths", deathComparison);

        add(accordion);
        add(new DashboardFooter());
    }
}
