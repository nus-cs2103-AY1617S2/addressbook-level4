package seedu.taskboss.logic.commands;

import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_START_DATE;


/**
 * Finds and lists all tasks in TaskBoss whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: n/NAME or sd/STARTDATETIME or ed/ENDDATETIME \n"
            + "Example: " + COMMAND_WORD + " n/meeting";

    private final String keywords;
    private final String prefix;

    public FindCommand(String pre, String keywords) {
        this.prefix = pre;
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        if (prefix.equals(PREFIX_NAME.toString())) {
            model.updateFilteredTaskListByName(keywords);
            return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
        } else if (prefix.equals(PREFIX_START_DATE.toString())) {
            model.updateFilteredTaskListByStartDateTime(keywords);
            return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
        } else {
            model.updateFilteredTaskListByEndDateTime(keywords);
            return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
        }
    }

}
