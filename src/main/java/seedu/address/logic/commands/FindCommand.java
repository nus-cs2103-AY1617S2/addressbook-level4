package seedu.address.logic.commands;

import java.util.Date;
import java.util.Set;

/**
 * Finds and lists all tasks in task manager whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " meet alice";

    private final Set<String> keywords;
    private final Date startDate;
    private final Date endDate;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
        this.startDate = null;
        this.endDate = null;
    }
    
    public FindCommand(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.keywords = null;
    }

    @Override
    public CommandResult execute() {
        if (keywords == null) {
            model.updateFilteredTaskList(startDate, endDate);  
        } else {
            model.updateFilteredTaskList(keywords);
        }
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
