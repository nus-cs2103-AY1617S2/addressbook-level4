package seedu.watodo.logic.parser;

import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.model.task.DateTime;

public class DateTimeParser {
    public static final Parser DATE_TIME_PARSER = new Parser();

    public static String parse(String args) throws IllegalValueException {
        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse(args);
        if (groups.size() == 0) {
            throw new IllegalValueException(DateTime.MESSAGE_DATETIME_CONSTRAINTS);
        }
        return groups.get(0).getText();
    }

}
