package seedu.task.logic.commands;

import java.util.Set;

import seedu.task.model.task.Date;

/**
 * Finds and lists all task in KIT whose name contains any of the argument keywords. Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    private static final boolean isExact = false;

    public static final String COMMAND_WORD_1 = "find";
    public static final String COMMAND_WORD_2 = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD_1 + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n" + "Example: " + COMMAND_WORD_1 + " do homework";

    private final Set<String> keywords;
    private  Date date;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
        this.date = new Date();
    }

    public FindCommand(Set<String> keywords, Date date) {
        this.keywords = keywords;
        this.date = date;

    }

    @Override
    public CommandResult execute() {
        model.sortTaskList();
        if (date.isNull()) {
            model.updateFilteredTaskList(keywords, isExact);
        } else {
            model.updateFilteredTaskList(keywords, date, isExact);
        }
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
