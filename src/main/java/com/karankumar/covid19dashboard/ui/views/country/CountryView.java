package com.karankumar.covid19dashboard.ui.views.country;

import com.karankumar.covid19dashboard.ui.MainView;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "country", layout = MainView.class)
@PageTitle("COVID-19: Country statistics")
public class CountryView extends VerticalLayout {

    public CountryView() {
        Accordion deathsAndCasesAccordion = new Accordion();
        deathsAndCasesAccordion.setWidthFull();
        VerticalLayout casesAndDeathsView = new CasesAndDeathsView();

        deathsAndCasesAccordion.add("Cases and deaths for one country", casesAndDeathsView);
        add(deathsAndCasesAccordion);

        add(new Anchor("https://covid19api.com/", "Source: COVID-19 API"));
    }
}
