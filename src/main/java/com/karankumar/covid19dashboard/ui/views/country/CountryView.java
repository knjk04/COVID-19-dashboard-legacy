package com.karankumar.covid19dashboard.ui.views.country;

import com.karankumar.covid19dashboard.backend.CountryName;
import com.karankumar.covid19dashboard.ui.MainView;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "country", layout = MainView.class)
@PageTitle("COVID-19: Country statistics")
public class CountryView extends VerticalLayout {
    public CountryView() {
        ComboBox<CountryName> country = new ComboBox<>("Country");
        country.setItems(CountryName.values());
        country.setRequired(true);
        country.setPlaceholder("Select a country");
        country.setMinWidth("20%");

        add(country);

        add(new Anchor("https://covid19api.com/", "Source: COVID-19 API"));
    }
}
