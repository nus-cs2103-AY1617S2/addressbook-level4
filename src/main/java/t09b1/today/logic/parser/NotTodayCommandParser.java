package t09b1.today.logic.parser;

import t09b1.today.commons.core.Messages;
import t09b1.today.logic.Logic;
import t09b1.today.logic.commands.Command;
import t09b1.today.logic.commands.IncorrectCommand;
import t09b1.today.logic.commands.NotTodayCommand;

/**
 * Parses input arguments and creates a new NotTodayCommand object
 */
public class NotTodayCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * NotTodayCommand and returns an NotTodayCommand object for execution.
     */
    public Command parse(String args, Logic logic) {
        if (!logic.isValidUIIndex(args)) {
            return new IncorrectCommand(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        return new NotTodayCommand(logic.parseUIIndex(args));
    }

}
