package seedu.tasklist.logic.parser;

import java.util.Date;
import java.util.List;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

public class DateParser {

    /**
     * Uses PrettyTime NLP library to parse the dates into a list of Date objects
     */
    public static List<Date> parse (String date) {
        List<Date> dates = new PrettyTimeParser().parse(date);
        return dates;
    }
}
