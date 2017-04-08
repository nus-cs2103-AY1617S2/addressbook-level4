package t09b1.today.logic.parser;

import t09b1.today.commons.core.Messages;
import t09b1.today.logic.Logic;
import t09b1.today.logic.commands.Command;
import t09b1.today.logic.commands.DeleteCommand;
import t09b1.today.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * DeleteCommand and returns an DeleteCommand object for execution.
     */
    public Command parse(String args, Logic logic) {
        if (!logic.isValidUIIndex(args)) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        return new DeleteCommand(logic.parseUIIndex(args));
    }

}
