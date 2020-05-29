package com.karankumar.covid19stats.ui;

import com.karankumar.covid19stats.ui.views.country.CountryView;
import com.karankumar.covid19stats.ui.views.global.GlobalView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route
public class MainView extends AppLayout {

    public MainView() {
        Tabs tabs = new Tabs();

        Tab globalTab = createTab(VaadinIcon.GLOBE, GlobalView.class, "Global");
        Tab countryTab = createTab(VaadinIcon.LOCATION_ARROW_CIRCLE, CountryView.class, "Country");

        tabs.add(globalTab, countryTab);
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);

        addToNavbar(true, tabs);
    }

    private static Tab createTab(VaadinIcon icon, Class<? extends Component> viewClass, String title) {
        return createTab(addLink(new RouterLink(null, viewClass), icon, title));
    }

    private static <T extends HasComponents> T addLink(T a, VaadinIcon icon, String title) {
        a.add(icon.create());
        a.add(title);
        return a;
    }

    private static Tab createTab (Component component) {
        final Tab tab = new Tab();
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab.add(component);
        return tab;
    }
}
