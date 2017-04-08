package seedu.today.logic.parser;

import seedu.today.commons.core.Messages;
import seedu.today.logic.Logic;
import seedu.today.logic.commands.Command;
import seedu.today.logic.commands.IncorrectCommand;
import seedu.today.logic.commands.TodayCommand;

/**
 * Parses input arguments and creates a new TodayCommand object
 */
public class TodayCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * TodayCommand and returns an TodayCommand object for execution.
     */
    public Command parse(String args, Logic logic) {
        // TODO allow multiple index
        if (!logic.isValidUIIndex(args)) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        return new TodayCommand(logic.parseUIIndex(args));
    }

}
