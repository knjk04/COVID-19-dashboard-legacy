package com.karankumar.covid19dashboard.backend.utils;

public final class DateUtil {

    private DateUtil() { }

    /**
     * Converts a date of the form yyyy-MM-ddTHH:MM:SSZ
     * @param dateAndTime the String from the API that contains both the date and the time
     * @return a date of the form dd-MM-yyyy
     */
    public static String formatDate(String dateAndTime) {
        String date = removeTimeFromDate(dateAndTime);

        String[] yearMonthDay = date.split("-");
        String year = yearMonthDay[0];
        String month = yearMonthDay[1];
        String day = yearMonthDay[2];
        String[] dayMonthYear = {day, month, year};

        return String.join("-", dayMonthYear);
    }

    private static String removeTimeFromDate(String dateAndTime) {
        return dateAndTime.split("T")[0];
    }
}
