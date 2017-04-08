package seedu.today.logic.parser;

import seedu.today.commons.core.Messages;
import seedu.today.logic.Logic;
import seedu.today.logic.commands.Command;
import seedu.today.logic.commands.DeleteCommand;
import seedu.today.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     */
    public Command parse(String args, Logic logic) {
        // TODO allow multiple index
        if (!logic.isValidUIIndex(args)) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        return new DeleteCommand(logic.parseUIIndex(args));
    }

}
