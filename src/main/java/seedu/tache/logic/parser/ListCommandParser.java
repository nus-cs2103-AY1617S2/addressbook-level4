//@@author A0139925U
package seedu.tache.logic.parser;

import static seedu.tache.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tache.logic.parser.CliSyntax.ALL_FILTER;
import static seedu.tache.logic.parser.CliSyntax.COMPLETED_FILTER;
import static seedu.tache.logic.parser.CliSyntax.DUE_THIS_WEEK_FILTER;
import static seedu.tache.logic.parser.CliSyntax.DUE_TODAY_FILTER;
import static seedu.tache.logic.parser.CliSyntax.FLOATING_FILTER;
import static seedu.tache.logic.parser.CliSyntax.TIMED_FILTER;
import static seedu.tache.logic.parser.CliSyntax.UNCOMPLETED_FILTER;

import seedu.tache.logic.commands.Command;
import seedu.tache.logic.commands.IncorrectCommand;
import seedu.tache.logic.commands.ListCommand;


/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     */
    public Command parse(String args) {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.equals("")) {
            switch(trimmedArgs) {
            case COMPLETED_FILTER:
            case UNCOMPLETED_FILTER:
            case TIMED_FILTER:
            case FLOATING_FILTER:
            case DUE_TODAY_FILTER:
            case DUE_THIS_WEEK_FILTER:
            case ALL_FILTER:
                return new ListCommand(trimmedArgs);
            default:
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }
        } else {
            return new ListCommand();
        }
    }

}
