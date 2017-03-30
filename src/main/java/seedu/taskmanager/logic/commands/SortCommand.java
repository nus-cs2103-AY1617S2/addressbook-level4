package seedu.taskmanager.logic.commands;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.logic.commands.exceptions.CommandException;

// @@author A0131278H
/**
 * Sorts tasks in the task manager according to start date or end date.
 */

public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String SORT_KEYWORD_STARTDATE = "s/";
    public static final String SORT_KEYWORD_ENDDATE = "e/";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts tasks by either startdate or enddate in the task manager. " + "Examples: " + COMMAND_WORD
            + " s/ OR " + COMMAND_WORD + " e/.";

    public static final String MESSAGE_SUCCESS_START = "Tasks sorted by start dates.";
    public static final String MESSAGE_SUCCESS_END = "Tasks sorted by end dates.";
    public static final String MESSAGE_SUCCESS_INVALID_KEYWORD = "Input keyword is invalid. "
            + "Tasks sorted by end dates by default.";

    private final String sortCriterion;

    /**
     * @param sortCriterion
     *            the criterion to sort tasks by
     */
    public SortCommand(String sortCriterion) throws IllegalValueException {
        this.sortCriterion = sortCriterion;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        model.sortTasks(sortCriterion);
        model.updateFilteredListToShowAll();

        switch (sortCriterion) {
        case SORT_KEYWORD_STARTDATE:
            return new CommandResult(MESSAGE_SUCCESS_START);
        case SORT_KEYWORD_ENDDATE:
            return new CommandResult(MESSAGE_SUCCESS_END);
        default:
            return new CommandResult(MESSAGE_SUCCESS_INVALID_KEYWORD);
        }

    }
}
// @@author
