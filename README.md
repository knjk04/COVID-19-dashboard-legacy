# Summary

[![Build Status](https://travis-ci.com/knjk04/COVID-19-dashboard.svg?branch=master)](https://travis-ci.com/knjk04/COVID-19-dashboard)
[![GitHub Student Gallery](https://img.shields.io/static/v1?label=Featured%20in&message=GitHub%20Student%20Gallery&color=00a8ff&logo=github&labelColor=192a56)](https://education.github.com/pack/gallery#c19Dash)

*For the most up-to-date version of the README, please refer to the version on the master branch.*

COVID-19 dashboard is a web app (made with Spring Boot and Vaadin) that displays COVID-19 statistics on a global level 
and on a per-country basis. 

[See it running live](https://covid19.karankumar.com/). As this app is not mobile-ready yet, for the best experience, please view it on either a desktop or laptop.

*Note: the fetched data is cached for 24 hours.*

# Features

*Note: the screenshots above are for illustrative purposes only. The actual UI may look slightly different. 
If any major changes are made, new screenshots will be uploaded.*

## Global overview

![Global dashboard](/media/global_dashboard.png)

The global page has:
- Live counters for the total number of deaths, total number of people recovered and total number of confirmed cases worldwide
- A table with information for different metrics for each country that there is data for
- Pie charts to visualise which countries have had the most COVID-19 deaths and confirmed cases

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

# Setup

## Prerequisites:

- JDK 11.0.5 or higher
- [Vaadin](https://vaadin.com/) Pro (or higher) license 
    - Free with the [GitHub student pack](https://education.github.com/pack)
    - [Free trial available](https://vaadin.com/pricing)
- Vaadin 14 or higher
- Node.js
- npm

## Running the app

1. Import the project as a Maven project into your favourite IDE
2. Run `Covid19DashboardApplication.java`
2. Go to `localhost:8080`

# Contributing

If you wish to contribute (thanks!), please first see the [contributing guide](https://github.com/knjk04/COVID-19-dashboard/blob/master/CONTRIBUTING.md).

# Acknowledgements

All COVID-19 data is from the [COVID-19 API](https://covid19api.com/) (the data is sourced from [Johns Hopkins](https://github.com/CSSEGISandData/COVID-19)).

Third-party libraries used:

- [OkHttp3](https://square.github.io/okhttp/)

- [JSON-Java (org.json)](https://github.com/stleary/JSON-java)

- [Caffeine](https://github.com/ben-manes/caffeine)

- [Apache Commons Lang 3](https://commons.apache.org/proper/commons-lang/)

# Disclaimer

*Note: this disclaimer is also present on the dashboard*

1. Please note that these figures, and all other information on this dashboard, are for demonstration purposes only. As ever, we strongly advise you corroborate any information from this dashboard with other data sources.
2. When comparing statistics between countries, please take into consideration any other relevant factors that could explain the differences between countries.
