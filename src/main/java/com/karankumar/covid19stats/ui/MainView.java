package com.karankumar.covid19stats.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends AppLayout {

    public MainView() {
        Tabs tabs = new Tabs();

        Tab globalTab = setTab("Global", VaadinIcon.GLOBE);
        Tab countryTab = setTab("Country", VaadinIcon.LOCATION_ARROW_CIRCLE);
        Tab[] tabArray = {globalTab, countryTab};
        setTabsThemeVariant(tabArray);

        tabs.add(globalTab, countryTab);
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);

        addToNavbar(tabs);
    }

    private void setTabsThemeVariant(Tab[] tab) {
        for (Tab t : tab) {
            t.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        }
    }

    private Tab setTab(String label, VaadinIcon icon) {
        Anchor a = new Anchor();
        a.add(icon.create());
        Tab tab = new Tab(label);
        tab.add(a);
        return tab;
    }

}
