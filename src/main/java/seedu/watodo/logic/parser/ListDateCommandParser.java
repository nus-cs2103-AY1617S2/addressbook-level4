package seedu.watodo.logic.parser;

import static seedu.watodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.NoSuchElementException;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.logic.commands.Command;
import seedu.watodo.logic.commands.IncorrectCommand;
import seedu.watodo.logic.commands.ListCommand;
import seedu.watodo.logic.commands.ListDateCommand;

//@@author A0139872R-reused
/**
 * Parses input arguments and creates a new ListDateCommand object
 */
public class ListDateCommandParser {

    private DateTimeParser dateTimeParser;

    /** Creates a ListDateCommandParser object that creates a new dateTimeParser object to parse date args */
    public ListDateCommandParser() {
        dateTimeParser = new DateTimeParser();
    }

    /**
     * Parses the given {@code String} of arguments in the context of the
     * ListDateCommand and returns a ListDateCommand object for execution.
     */
    public Command parse(String args) {
        try {
            dateTimeParser.parse(args);
            return new ListDateCommand(dateTimeParser.getStartDate(), dateTimeParser.getEndDate());
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
}
