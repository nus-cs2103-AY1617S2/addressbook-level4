package seedu.address.logic.parser;

import java.util.Optional;

import seedu.address.logic.commands.CalendarViewCommand;
import seedu.address.logic.commands.Command;

//@@author A0124377A

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class CalendarViewCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * CalendarViewCommand and returns an CalendarViewCommand object for
     * execution.
     */
    public static final int DEFAULT_WEEK = 1;

    public Command parse(String args) {
        Optional<Integer> index = ParserUtil.parseIntegerAlone(args);
        if (!index.isPresent()) {
            return new CalendarViewCommand(DEFAULT_WEEK);
        } else {
            return new CalendarViewCommand(index.get());
        }
    }

}
