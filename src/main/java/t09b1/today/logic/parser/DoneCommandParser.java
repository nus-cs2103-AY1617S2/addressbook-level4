package t09b1.today.logic.parser;

import t09b1.today.commons.core.Messages;
import t09b1.today.logic.Logic;
import t09b1.today.logic.commands.Command;
import t09b1.today.logic.commands.DoneCommand;
import t09b1.today.logic.commands.IncorrectCommand;

//@@author A0093999Y
/**
 * Parses input arguments and creates a new DoneCommand object
 */
public class DoneCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * DoneCommand and returns an DoneCommand object for execution.
     */
    public Command parse(String args, Logic logic) {
        if (!logic.isValidUIIndex(args)) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        return new DoneCommand(logic.parseUIIndex(args));
    }

}
