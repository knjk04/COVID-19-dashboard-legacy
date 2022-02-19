package com.karankumar.covid19dashboard.ui.views.help;

import com.karankumar.covid19dashboard.ui.MainView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "feedback", layout = MainView.class)
@PageTitle("COVID-19: Feedback")
public class Feedback extends HorizontalLayout {
    public Feedback() {
        VerticalLayout verticalLayout = new VerticalLayout();

        Span span = new Span("Thank you for trying the COVID-19 dashboard. If you have any suggestions or if you " +
                "found any bugs, please raise an issue on GitHub: https://github.com/knjk04/COVID-19-dashboard");

        verticalLayout.add(span);
        verticalLayout.setAlignItems(Alignment.CENTER);

        this.add(verticalLayout);
        this.setSizeFull();
        this.setAlignItems(Alignment.CENTER);

    }
}
