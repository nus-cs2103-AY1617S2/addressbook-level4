package seedu.taskboss.logic;

import javafx.collections.ObservableList;
import seedu.taskboss.commons.exceptions.BuiltInCategoryException;
import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.CommandResult;
import seedu.taskboss.logic.commands.exceptions.CommandException;
import seedu.taskboss.logic.commands.exceptions.InvalidDatesException;
import seedu.taskboss.model.category.Category;
import seedu.taskboss.model.task.ReadOnlyTask;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws IllegalValueException
     * @throws BuiltInCategoryException
     */
    CommandResult execute(String commandText) throws CommandException,
        IllegalValueException, InvalidDatesException, BuiltInCategoryException;

    /** Returns the filtered list of tasks */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

    //@@author A0143157J
    /** Returns the filtered list of tasks according to specified category */
    void updateFilteredTaskListByCategory(Category category);

}
