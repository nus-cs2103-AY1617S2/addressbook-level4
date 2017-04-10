//@@author A0139925U
package seedu.tache.logic.parser;

import static seedu.tache.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tache.logic.parser.CliSyntax.FILTER_ALL;
import static seedu.tache.logic.parser.CliSyntax.FILTER_COMPLETED;
import static seedu.tache.logic.parser.CliSyntax.FILTER_DUE_THIS_WEEK;
import static seedu.tache.logic.parser.CliSyntax.FILTER_DUE_TODAY;
import static seedu.tache.logic.parser.CliSyntax.FILTER_FLOATING;
import static seedu.tache.logic.parser.CliSyntax.FILTER_OVERDUE;
import static seedu.tache.logic.parser.CliSyntax.FILTER_TIMED;
import static seedu.tache.logic.parser.CliSyntax.FILTER_UNCOMPLETED;

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
            case FILTER_COMPLETED:
            case FILTER_UNCOMPLETED:
            case FILTER_TIMED:
            case FILTER_FLOATING:
            case FILTER_DUE_TODAY:
            case FILTER_DUE_THIS_WEEK:
            case FILTER_OVERDUE:
            case FILTER_ALL:
                return new ListCommand(trimmedArgs);
            default:
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }
        } else {
            return new ListCommand();
        }
    }

}
