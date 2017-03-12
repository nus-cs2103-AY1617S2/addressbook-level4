package seedu.taskboss.logic.commands;

import java.util.Set;


/**
 * Finds and lists all tasks in TaskBoss whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String PREFIX_NAME = "/n";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: n/NAME or sd/STARTDATETIME or ed/ENDDATETIME \n"
            + "Example: " + COMMAND_WORD + " n/meeting";

    private final Set<String> keywords;
    private final String prefix;

    public FindCommand(String pre, Set<String> keywords) {
        this.prefix = pre;
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        if (prefix.equals(PREFIX_NAME)) {
            model.updateFilteredTaskListByName(keywords);
            return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
        } else {
            model.updateFilteredTaskList(keywords);
            return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
        }
    }

}
