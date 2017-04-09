package seedu.taskit.logic.parser;

import static seedu.taskit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskit.logic.parser.CliSyntax.DONE;
import static seedu.taskit.logic.parser.CliSyntax.LIST_ALL;
import static seedu.taskit.logic.parser.CliSyntax.LIST_DEADLINE;
import static seedu.taskit.logic.parser.CliSyntax.LIST_EVENT;
import static seedu.taskit.logic.parser.CliSyntax.LIST_FLOATING;
import static seedu.taskit.logic.parser.CliSyntax.LIST_OVERDUE;
import static seedu.taskit.logic.parser.CliSyntax.LIST_PRIORITY_HIGH;
import static seedu.taskit.logic.parser.CliSyntax.LIST_PRIORITY_LOW;
import static seedu.taskit.logic.parser.CliSyntax.LIST_PRIORITY_MEDIUM;
import static seedu.taskit.logic.parser.CliSyntax.LIST_TODAY;
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

    //TODO think a better way to do this
    private static final List<String> PARAMETERS = Arrays.asList(LIST_ALL,LIST_DEADLINE, LIST_FLOATING, LIST_EVENT,
            LIST_TODAY, LIST_OVERDUE, LIST_PRIORITY_LOW, LIST_PRIORITY_MEDIUM, LIST_PRIORITY_HIGH,DONE,UNDONE);

    public Command parse(String args) {
        Optional<String> parameters = Optional.of(args);
        if (!parameters.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
        String parameter = parameters.get().trim().toLowerCase();
        if(!isValidParameter(parameter)){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
        return new ListCommand(parameter);
    }

    private boolean isValidParameter(String parameter) {
        return PARAMETERS.contains(parameter);
    }
}
