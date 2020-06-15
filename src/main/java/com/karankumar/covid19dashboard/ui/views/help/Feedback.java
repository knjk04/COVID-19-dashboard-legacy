package com.karankumar.covid19dashboard.ui.views.help;

import com.karankumar.covid19dashboard.ui.MainView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "feedback", layout = MainView.class)
@PageTitle("COVID-19: Feedback")
public class Feedback extends VerticalLayout {
    public Feedback() {
        Span span1 = new Span("Thank you for trying the COVID-19 dashboard. If you have any suggestions or if you " +
                "found any bugs, you can get in touch via one of the following methods: ");

        Anchor github = new Anchor("https://github.com/knjk04/COVID-19-dashboard", "GitHub");
        Text option1 = new Text("1. (Preferred) Raise an issue on ");
        Span span2 = new Span(option1, github);

        Text option2 = new Text("2. Send a message via the ");
        Anchor contactForm = new Anchor("https://karankumar.com/contact.html", "contact form");
        Text option2a = new Text(" with the subject line 'COVID-19 dashboard'");
        Span span3 = new Span(option2, contactForm, option2a);

        add(span1, span2, span3);

        setJustifyContentMode(JustifyContentMode.CENTER);
    }
}
