package seedu.address.logic.parser;

import seedu.address.commons.core.Messages;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.NotDoneCommand;

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
        // TODO allow multiple index
        if (!logic.isValidUIIndex(args)) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        return new NotDoneCommand(logic.parseUIIndex(args));
    }

}
