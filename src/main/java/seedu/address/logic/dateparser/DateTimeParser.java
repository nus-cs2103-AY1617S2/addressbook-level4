package seedu.address.logic.dateparser;

import java.util.List;

import com.joestelmach.natty.DateGroup;

//@@author A0162877N
/**
 * The API of the DateTimeParser component.
 */
public interface DateTimeParser {

    /**
     * Parses the given input value to the DateTimeParser
     */
    List<DateGroup> parse(String value);

}
