package com.karankumar.covid19dashboard.ui.views.country.comparison;

import com.karankumar.covid19dashboard.backend.api.dayone.CaseType;
import com.karankumar.covid19dashboard.backend.domain.dayone.CountryTotal;

public class ConfirmedCaseComparisonView<T extends CountryTotal> extends ComparisonView<T> {
    public ConfirmedCaseComparisonView() {
        super(CaseType.CONFIMRED);
        add(createResetAndCountryComboBox());
    }
}
