package seedu.tasklist.logic.parser;

import java.util.Date;
import java.util.List;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import seedu.tasklist.commons.exceptions.IllegalValueException;

public class DateParser {

    public static final String NO_DATES_MESSAGE = "No dates were detected, please enter a valid date after /d.";
    public static final String EXCESS_DATES = "Please enter only one date for Deadlines and two dates for Events.";

    /**
     * Uses PrettyTime NLP library to parse the dates into a list of Date objects
     */
    public static List<Date> parse (String date) throws IllegalValueException {
        List<Date> dates = new PrettyTimeParser().parse(date);
        System.out.println(dates.size());
        if (dates.isEmpty()) {
            throw new IllegalValueException(NO_DATES_MESSAGE);
        }
        if (dates.size() > 2) {
            throw new IllegalValueException(EXCESS_DATES);
        }
        return dates;
    }
}
