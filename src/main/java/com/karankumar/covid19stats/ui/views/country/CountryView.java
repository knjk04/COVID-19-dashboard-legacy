package com.karankumar.covid19stats.ui.views.country;

import com.karankumar.covid19stats.ui.MainView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "country", layout = MainView.class)
@PageTitle("Country")
public class CountryView extends VerticalLayout {
    public CountryView() {
        add(new TextField());
    }
}
