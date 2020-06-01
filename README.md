# Summary

*For the most up-to-date version of the README, please refer to the version on the master branch.*

COVID-19 dashboard is a web app that displays COVID-19 statistics on a global level and, coming soon, on a per-country basis. This is made using Spring Boot and Vaadin.

The [COVID-19 API](https://covid19api.com/) (the data is sourced from [Johns Hopkins](https://github.com/CSSEGISandData/COVID-19)).

![Global dashboard](/media/global-dashboard.png)

*Note: the screenshot above is for illustrative purposes only. The actual UI may look slightly different. 
If any major changes are made, a new screenshot will be added.*

## Setup

### Prerequisites:

- JDK 11.0.5 or higher
- [Vaadin](https://vaadin.com/) Pro (or higher) license (free with the [GitHub student pack](https://education.github.com/pack))
- Vaadin 14 or higher
- Node.js
- npm

### Running the app

1. Run Covid19DashboardApplication.java
2. Go to localhost:8080/global

If you see a a JSON Exception, refresh the page until you it goes away. This is a [known issue](https://github.com/knjk04/COVID-19-dashboard/issues/5) to do with reaching the maximum number of API requests.

## Contributing

If you wish to contribute (thanks!), please first see the [contributing guide](https://github.com/knjk04/COVID-19-dashboard/blob/master/CONTRIBUTING.md).

## Acknowledgements

Libraries used:

- [OkHttp3](https://square.github.io/okhttp/)

- [JSON-Java (org.json)](https://github.com/stleary/JSON-java)

- [Caffeine](https://github.com/ben-manes/caffeine)

