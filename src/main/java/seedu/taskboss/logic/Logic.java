package seedu.taskboss.logic;

import javafx.collections.ObservableList;
import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.CommandResult;
import seedu.taskboss.logic.commands.exceptions.CommandException;
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
     */
    CommandResult execute(String commandText) throws CommandException, IllegalValueException;

    /** Returns the filtered list of persons */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

}
