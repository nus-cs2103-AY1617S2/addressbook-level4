package seedu.tache.logic;

import javafx.collections.ObservableList;
import seedu.tache.logic.commands.CommandResult;
import seedu.tache.logic.commands.exceptions.CommandException;
import seedu.tache.model.task.ReadOnlyTask;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     */
    CommandResult execute(String commandText) throws CommandException;

    /** Returns the filtered list of tasks */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

    //@@author A0142255M
    /**
     * Returns the no. of timed tasks in String form.
     */
    String getTimedTasks();

    /**
     * Returns the no. of floating tasks in String form.
     */
    String getFloatingTasks();

}
