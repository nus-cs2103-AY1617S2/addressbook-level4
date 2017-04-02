package seedu.geekeep.logic;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.geekeep.logic.commands.CommandResult;
import seedu.geekeep.logic.commands.exceptions.CommandException;
import seedu.geekeep.model.task.ReadOnlyTask;

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

    //@@author A0139438W
    /** Returns the filtered list of floating tasks */
    ObservableList<ReadOnlyTask> getFilteredFloatingTaskList();

    /** Returns the filtered list of floating tasks */
    ObservableList<ReadOnlyTask> getFilteredEventList();

    /** Returns the filtered list of floating tasks */
    ObservableList<ReadOnlyTask> getFilteredDeadlineList();

    /** Retruns command history */
    List<String> getCommandHistory();

}
