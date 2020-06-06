package com.karankumar.covid19dashboard.ui.views.country.comparison;

import com.karankumar.covid19dashboard.backend.api.dayone.CaseType;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryTotal;

public class DeathComparisonView<T extends CountryTotal> extends ComparisonView<T> {
    public DeathComparisonView() {
        super(CaseType.DEATHS);
        add(createResetAndCountryComboBox());
    }
}
