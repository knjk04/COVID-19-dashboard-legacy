# Summary

*For the most up-to-date version of the README, please refer to the version on the master branch.*

COVID-19 dashboard is a web app (made with Spring Boot and Vaadin) that displays COVID-19 statistics on a global level 
and on a per-country basis. 

*Note: the fetched data is cached for 24 hours.*

# Features

## Global overview

![Global dashboard](/media/global_dashboard.png)

## Change in cases over time for a specific country

![Country dashboard](/media/cases_and_deaths_one_country.png)

See how the number of deaths has changed over time, since the first COVID-19 death, as the number of confirmed cases
have changed over time (also since the first confirmed case).

## Case comparison between countries

![Case comparison](/media/cases_comparison.png)

Compare one country's rate of confirmed cases with others (the screenshot above shows a comparison with
one other country, but you can compare a country with multiple other countries).

## Death comparison between countries

![Death comparison](/media/deaths_comparison.png)

Or compare different countries' COVID-19 death rates.

*Note: the screenshots above are for illustrative purposes only. The actual UI may look slightly different. 
If any major changes are made, new screenshots will be uploaded.*

## Setup

## Prerequisites:

- JDK 11.0.5 or higher
- [Vaadin](https://vaadin.com/) Pro (or higher) license 
    - Free with the [GitHub student pack](https://education.github.com/pack)
    - [Free trial available](https://vaadin.com/pricing)
- Vaadin 14 or higher
- Node.js
- npm

# Running the app

1. Run `Covid19DashboardApplication.java`
2. Go to `localhost:8080/global`
3. Switch to the country tab
4. Swtich back to the global tab

If you see a a JSON Exception, keep on refreshing the page until you it goes away (it should eventually). This is a [known issue](https://github.com/knjk04/COVID-19-dashboard/issues/5) to do with reaching the maximum number of API requests.

# Contributing

If you wish to contribute (thanks!), please first see the [contributing guide](https://github.com/knjk04/COVID-19-dashboard/blob/master/CONTRIBUTING.md).

# Acknowledgements

The [COVID-19 API](https://covid19api.com/) (the data is sourced from [Johns Hopkins](https://github.com/CSSEGISandData/COVID-19)).

Third-party libraries used:

- [OkHttp3](https://square.github.io/okhttp/)

- [JSON-Java (org.json)](https://github.com/stleary/JSON-java)

- [Caffeine](https://github.com/ben-manes/caffeine)

