package com.karankumar.covid19dashboard.ui.component;

import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Footer;

public class DashboardFooter extends Footer {
    public DashboardFooter() {
        super(
                new Anchor("https://covid19api.com/", "Source: COVID-19 API"),
                new HtmlComponent("br"),
                new HtmlComponent("br"),
                new Text("Disclaimers:"),
                new HtmlComponent("br"),
                new Text("1. Please note that these figures, and all other information on this dashboard, are for " +
                        "demonstration purposes only. As ever, we strongly advise you corroborate any information " +
                        "from this dashboard with other data sources."),
                new HtmlComponent("br"),
                new Text("2. When comparing statistics between countries, please take into consideration " +
                        "any other relevant factors that could explain the differences between countries.")
        );
    }
}
