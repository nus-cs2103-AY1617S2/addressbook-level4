package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.ShowcaseCommand;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class ShowcaseCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * DeleteCommand and returns an DeleteCommand object for execution.
     */
    public Command parse(String args) {
        args = args.trim();
        int numberOfTasks = Integer.parseInt(args);
        return new ShowcaseCommand(numberOfTasks);
    }

}
