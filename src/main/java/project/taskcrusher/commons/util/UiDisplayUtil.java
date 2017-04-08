package project.taskcrusher.commons.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import project.taskcrusher.model.event.Location;
import project.taskcrusher.model.event.Timeslot;
import project.taskcrusher.model.shared.Priority;
import project.taskcrusher.model.task.Deadline;

// @@author A0127737X
public class UiDisplayUtil {
    public static final String[] PARSE_PATTERNS = { "yyyy-MM-dd hh:mma", "yyyy-MM-dd", "MM-dd hh:mma", "hh:mma" };
    public static final int FORMAT_DATE_ABSOLUTE = 0;
    public static final int FORMAT_THIS_YEAR = 2;
    public static final int FORMAT_DATE_RELATIVE = 3;
    private static final String MESSAGE_NO_DEADLINE = "no deadline";

    /**
     * returns a string representation of the given Date in a user-friendly format
     */
    public static String deadlineForUi(Deadline deadline) {
        assert deadline != null;
        if (!deadline.hasDeadline()) {
            return MESSAGE_NO_DEADLINE;
        }

        Date date = deadline.getDate().get();
        SimpleDateFormat formatter;
        String prepend = "By ";
        if (isToday(date)) {
            formatter = new SimpleDateFormat(PARSE_PATTERNS[FORMAT_DATE_RELATIVE]);
            prepend = "Today ";
        } else if (isThisYear(date)) {
            formatter = new SimpleDateFormat(PARSE_PATTERNS[FORMAT_THIS_YEAR]);
        } else {
            formatter = new SimpleDateFormat(PARSE_PATTERNS[0]);
        }
        return prepend + formatter.format(date);
    }

    private static boolean isThisYear(Date d) {
        Date now = new Date();
        SimpleDateFormat yearChecker = new SimpleDateFormat("yyyy");
        return yearChecker.format(now).equals(yearChecker.format(d));
    }

    private static boolean isToday(Date d) {
        Date now = new Date();
        SimpleDateFormat dateChecker = new SimpleDateFormat("yyyyMMdd");
        return dateChecker.format(now).equals(dateChecker.format(d));
    }

    public static String timeslotAsStringForUi(Timeslot timeslot) {
        assert timeslot != null;
        String endFormat, startFormat, prepend = "";
        if (isSameDate(timeslot.start, timeslot.end)) {
            endFormat = PARSE_PATTERNS[FORMAT_DATE_RELATIVE];
        } else {
            endFormat = PARSE_PATTERNS[FORMAT_DATE_ABSOLUTE];
        }
        if (isToday(timeslot.start)) {
            startFormat = PARSE_PATTERNS[FORMAT_DATE_RELATIVE];
            prepend = "Today ";
        } else if (isThisYear(timeslot.start)) {
            startFormat = PARSE_PATTERNS[FORMAT_THIS_YEAR];
            if (isThisYear(timeslot.end)) {
                endFormat = PARSE_PATTERNS[FORMAT_THIS_YEAR];
            }
        } else {
            startFormat = PARSE_PATTERNS[FORMAT_DATE_ABSOLUTE];
        }

        SimpleDateFormat sdf = new SimpleDateFormat(startFormat);
        prepend += sdf.format(timeslot.start) + " to ";
        sdf.applyPattern(endFormat);
        prepend += sdf.format(timeslot.end);
        return prepend;
    }

    private static boolean isSameDate(Date d1, Date d2) {
        SimpleDateFormat dateChecker = new SimpleDateFormat("yyyyMMdd");
        return dateChecker.format(d1).equals(dateChecker.format(d2));
    }

    public static String priorityForUi(Priority p) {
        return "p=" + p.priority;
    }

    public static String locationForUi(Location location) {
        return location.hasLocation() ? "@ " + location.location : "";
    }

}
