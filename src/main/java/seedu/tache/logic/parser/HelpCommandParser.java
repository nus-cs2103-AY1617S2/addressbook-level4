//@@author A0139961U
package seedu.tache.logic.parser;

import static seedu.tache.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.tache.logic.commands.AddCommand;
import seedu.tache.logic.commands.ClearCommand;
import seedu.tache.logic.commands.Command;
import seedu.tache.logic.commands.CompleteCommand;
import seedu.tache.logic.commands.DeleteCommand;
import seedu.tache.logic.commands.EditCommand;
import seedu.tache.logic.commands.ExitCommand;
import seedu.tache.logic.commands.FindCommand;
import seedu.tache.logic.commands.HelpCommand;
import seedu.tache.logic.commands.IncorrectCommand;
import seedu.tache.logic.commands.ListCommand;
import seedu.tache.logic.commands.LoadCommand;
import seedu.tache.logic.commands.NextCommand;
import seedu.tache.logic.commands.PrevCommand;
import seedu.tache.logic.commands.SaveCommand;
import seedu.tache.logic.commands.SelectCommand;
import seedu.tache.logic.commands.UndoCommand;
import seedu.tache.logic.commands.ViewCommand;


/**
 * Parses input arguments and creates a new HelpCommand object
 */
public class HelpCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the HelpCommand
     * and returns an HelpCommand object for execution.
     */
    public Command parse(String args) {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.equals("")) {
            switch(trimmedArgs) {
            case AddCommand.COMMAND_WORD:
            case ClearCommand.COMMAND_WORD:
            case CompleteCommand.COMMAND_WORD:
            case DeleteCommand.COMMAND_WORD:
            case EditCommand.COMMAND_WORD:
            case ExitCommand.COMMAND_WORD:
            case FindCommand.COMMAND_WORD:
            case ListCommand.COMMAND_WORD:
            case LoadCommand.COMMAND_WORD:
            case SaveCommand.COMMAND_WORD:
            case SelectCommand.COMMAND_WORD:
            case UndoCommand.COMMAND_WORD:
            case PrevCommand.COMMAND_WORD:
            case NextCommand.COMMAND_WORD:
            case ViewCommand.COMMAND_WORD:
                return new HelpCommand(trimmedArgs);
            default:
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
            }
        } else {
            return new HelpCommand();
        }
    }

}
