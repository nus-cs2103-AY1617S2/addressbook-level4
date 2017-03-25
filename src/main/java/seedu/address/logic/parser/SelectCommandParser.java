package seedu.address.logic.parser;

import seedu.address.commons.core.Messages;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.SelectCommand;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SelectCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     */
    public Command parse(String args, Logic logic) {
        // TODO allow multiple index
        if (!logic.isValidUIIndex(args)) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        return new SelectCommand(logic.parseUIIndex(args));
    }

}
