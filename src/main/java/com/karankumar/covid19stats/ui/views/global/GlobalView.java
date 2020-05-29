package com.karankumar.covid19stats.ui.views.global;

import com.karankumar.covid19stats.ui.MainView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "global", layout = MainView.class)
@PageTitle("Global")
public class GlobalView extends VerticalLayout {
    public GlobalView() {

    }
}
