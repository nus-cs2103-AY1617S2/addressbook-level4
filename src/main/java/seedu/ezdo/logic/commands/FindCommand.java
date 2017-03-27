package seedu.ezdo.logic.commands;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.TaskDate;

/**
 * Finds and lists all tasks in ezDo whose name contains any of the argument
 * keywords. Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String SHORT_COMMAND_WORD = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n" + "Example: " + COMMAND_WORD + " buy milk clean p/3";
/*
    private final Set<String> keywords;
    private final Optional<Priority> priorityToMatch;
    private final Optional<TaskDate> startDateToMatch;
    private final Optional<TaskDate> dueDateToMatch;
    private final Set<String> tagsToMatch;*/
    private final ArrayList<Object> listToCompare;
    private final boolean searchByStartDate;
    private final boolean searchByDueDate;

    public FindCommand(ArrayList<Object> listToCompare, boolean searchByStartDate, boolean searchByDueDate) {
        this.listToCompare = listToCompare;
        this.searchByStartDate = searchByStartDate;
        this.searchByDueDate = searchByDueDate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(listToCompare, searchByStartDate, searchByDueDate);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
