package seedu.taskit.logic.parser;

import static seedu.taskit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskit.logic.parser.CliSyntax.DONE;
import static seedu.taskit.logic.parser.CliSyntax.ALL;
import static seedu.taskit.logic.parser.CliSyntax.DEADLINE;
import static seedu.taskit.logic.parser.CliSyntax.EVENT;
import static seedu.taskit.logic.parser.CliSyntax.FLOATING;
import static seedu.taskit.logic.parser.CliSyntax.OVERDUE;
import static seedu.taskit.logic.parser.CliSyntax.PRIORITY_HIGH;
import static seedu.taskit.logic.parser.CliSyntax.PRIORITY_LOW;
import static seedu.taskit.logic.parser.CliSyntax.PRIORITY_MEDIUM;
import static seedu.taskit.logic.parser.CliSyntax.TODAY;
import static seedu.taskit.logic.parser.CliSyntax.UNDONE;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import seedu.taskit.logic.commands.Command;
import seedu.taskit.logic.commands.IncorrectCommand;
import seedu.taskit.logic.commands.ListCommand;

//@@author A0141872E
/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser {

    private static final List<String> PARAMETERS = Arrays.asList(ALL,DEADLINE, FLOATING, EVENT,
            TODAY, OVERDUE, PRIORITY_LOW, PRIORITY_MEDIUM, PRIORITY_HIGH,DONE,UNDONE);

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     */
    public Command parse(String args) {
        Optional<String> parseParameter = Optional.of(args);
        if (!parseParameter.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
        String parameter = parseParameter.get().trim().toLowerCase();
        if(!isValidParameter(parameter)){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
        return new ListCommand(parameter);
    }

    /**
     * Only parameter defined in parameter lists are allowed
     */
    private boolean isValidParameter(String parameter) {
        return PARAMETERS.contains(parameter);
    }
}
