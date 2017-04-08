package seedu.today.logic.parser;

import seedu.today.commons.core.Messages;
import seedu.today.logic.Logic;
import seedu.today.logic.commands.Command;
import seedu.today.logic.commands.IncorrectCommand;
import seedu.today.logic.commands.NotTodayCommand;

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
