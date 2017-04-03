package typetask.logic.parser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
//@@author A0139926R
/**
 * Parser class for handling date and time using the
 * Natty library.
 * @author Abel
 *
 */
public class DateParser {

    private static final int day = 0;
    private static final int month = 1;
    private static final int dayDate = 2;
    private static final int time = 3;
    private static final int year = 5;
    private static final int dateFromUser = 0;
    /**
     * Private constructor to prevent instantiation.
     */
    private DateParser() {
    }

    /**
     * Parses given date and time string and returns
     * an array of date time that we are interested in capturing.
     * Usually start and end dates or just deadline date.
     * If no dates are found, empty list is returned.
     * @param dateTimeString String containing date and time to be parsed.
     * @return A list of Dates found in String.
     */
    public static List<Date> parse(String dateTimeString) {
        assert dateTimeString != null && !dateTimeString.isEmpty();
        Parser parser = new Parser(TimeZone.getDefault());
        List<DateGroup> groups = parser.parse(dateTimeString);
        if (groups.isEmpty() || groups.get(0) == null) {
            return new ArrayList<>();
        }


        DateGroup group = groups.get(dateFromUser);
        List<Date> dates = group.getDates();

        // If time is inferred and not explicitly stated by user
        // Reset time because it would produce the current time
        boolean isTimeInferred = group.isTimeInferred();
        if (isTimeInferred) {
            resetTime(dates);
        }

        return dates;
    }

    /**
     * Resets the time fields in the list of dates to zeroes.
     * @param dates List of dates to be reset.
     */
    private static void resetTime(List<Date> dates) {
        if (dates == null) {
            return;
        }

        for (Date date: dates) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 0);
            date.setTime(calendar.getTime().getTime());
        }
    }
    //@@author A0139926R
    /**
     * Uses @param date to get date and time
     * @return list of dates
     */
    public static List<Date> getDate(String date) {
        assert date != null;
        List<Date> dates = DateParser.parse(date);
        return dates;
    }
    /**
     * Gets the list of dates from @param dates
     * @return date in String format
     */
    public static String getDateString(List<Date> dates) {
        String finalizedDate;
        String nattyDate = dates.get(dateFromUser).toString();
        String[] splitDate = nattyDate.split(" ");
        finalizedDate = splitDate[day] + " " + splitDate[month] + " " + splitDate[dayDate] +
                " " + splitDate[year] + " " + splitDate[time];
        return finalizedDate;
    }
    /**
     * Compares @param startDate and @param endDate
     * Checks event schedule
     * Checks only if there is a startDate and endDate
     * @return true if endDate is not before startDate, otherwise false
     */
    public static boolean checkValidSchedule(List<Date> startDate, List<Date> endDate) {
        boolean isValidDate = false;
        if (!startDate.isEmpty() && !endDate.isEmpty()) {
            Date sDate = startDate.get(dateFromUser);
            Date eDate = endDate.get(dateFromUser);
            isValidDate = checkValidEventDate(sDate, eDate);
        }
        return isValidDate;
    }
    /**
     * Compares @param startDate and @param endDate
     * @return true if endDate is not before startDate, otherwise false
     */
    public static boolean checkValidEventDate(Date startDate, Date endDate) {
        boolean isValidDate = false;
        if (startDate.before(endDate)) {
            isValidDate = true;
        }
        return isValidDate;
    }
    /**
     * Checks @param date if the list of dates is empty or not
     * @return true if there is a date in the list
     */
    public static boolean checkValidDateFormat(List<Date> date) {
        boolean isValidDate = false;
        if (!date.isEmpty()) {
            isValidDate = true;
        }
        return isValidDate;
    }

}
