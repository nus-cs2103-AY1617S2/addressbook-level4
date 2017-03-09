package seedu.address.logic.parser;

import java.util.List;
import com.joestelmach.natty.DateGroup;

public interface DateTime {

    /**
     * Parses the given input value to the DateTimeParser
     */
    List<DateGroup> parse(String value);
    
}
