package t09b1.today.logic.parser;

import t09b1.today.commons.core.Messages;
import t09b1.today.logic.Logic;
import t09b1.today.logic.commands.Command;
import t09b1.today.logic.commands.IncorrectCommand;
import t09b1.today.logic.commands.NotDoneCommand;

//@@author A0093999Y
/**
 * Parses input arguments and creates a new NotDoneCommand object
 */
public class NotDoneCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * NotDoneCommand and returns an NotDoneCommand object for execution.
     */
    public Command parse(String args, Logic logic) {
        if (!logic.isValidUIIndex(args)) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        return new NotDoneCommand(logic.parseUIIndex(args));
    }

}
