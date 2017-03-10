package seedu.address.logic.parser;

import java.util.List;

import com.joestelmach.natty.DateGroup;

/**
 * The API of the DateTimeParser component.
 */
public interface DateTimeParser {

    /**
     * Parses the given input value to the DateTimeParser
     */
    List<DateGroup> parse(String value);

}
