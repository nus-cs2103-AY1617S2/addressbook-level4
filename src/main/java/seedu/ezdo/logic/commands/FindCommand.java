package seedu.ezdo.logic.commands;

import seedu.ezdo.commons.util.SearchParameters;
//@@author A0141010L
/**
 * Finds and lists all tasks in ezDo whose name contains any of the argument
 * keywords. Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String SHORT_COMMAND_WORD = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [KEYWORD] [MORE_KEYWORDS] [p/PRIORITY] [s/START_DATE] [d/DUE_DATE] [t/TAG...]\n"
            + "Example: " + COMMAND_WORD + " buy milk p/3 t/shopping";

    private final SearchParameters searchParameters;

    public FindCommand(SearchParameters searchParameters) {
        this.searchParameters = searchParameters;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(searchParameters);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
