package seedu.address.logic.parser;

import seedu.address.commons.core.Messages;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.NotTodayCommand;

/**
 * Parses input arguments and creates a new NotTodayCommand object
 */
public class NotTodayCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * TodayCommand and returns an NotTodayCommand object for execution.
     */
    public Command parse(String args, Logic logic) {
        if (!logic.isValidUIIndex(args)) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        return new NotTodayCommand(logic.parseUIIndex(args));
    }

}
