//@@author A0163744B
package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.logic.parser.CliSyntax.LIST_COMMAND_ADDED;
import static seedu.task.logic.parser.CliSyntax.LIST_COMMAND_COMPLETE;
import static seedu.task.logic.parser.CliSyntax.LIST_COMMAND_DUE;
import static seedu.task.logic.parser.CliSyntax.LIST_COMMAND_END;
import static seedu.task.logic.parser.CliSyntax.LIST_COMMAND_INCOMPLETE;
import static seedu.task.logic.parser.CliSyntax.LIST_COMMAND_START;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.ListCommand;
import seedu.task.logic.commands.ListCommand.ListCommandOption;

/**
 * Parses input arguments for the list command and creates a new ListCommand object
 */
public class ListCommandParser {
    private static final String EMPTY_STRING = "";
    private ListCommandOption commandOption;

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     */
    public Command parse(String args) {
        switch(args.trim()) {
        case EMPTY_STRING:
            commandOption = ListCommandOption.ALL;
            break;
        case LIST_COMMAND_COMPLETE:
            commandOption = ListCommandOption.COMPLETE;
            break;
        case LIST_COMMAND_INCOMPLETE:
            commandOption = ListCommandOption.INCOMPLETE;
            break;
        case LIST_COMMAND_ADDED:
            commandOption = ListCommandOption.ID;
            break;
        case LIST_COMMAND_DUE:
            commandOption = ListCommandOption.DUE;
            break;
        case LIST_COMMAND_START:
            commandOption = ListCommandOption.START;
            break;
        case LIST_COMMAND_END:
            commandOption = ListCommandOption.END;
            break;
        default:
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        return new ListCommand(commandOption);
    }
}
