package com.karankumar.covid19dashboard.ui.component;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Footer;

public class DashboardFooter extends Footer {
    public DashboardFooter() {
        super(new Anchor("https://covid19api.com/", "Source: COVID-19 API"));
    }
}
