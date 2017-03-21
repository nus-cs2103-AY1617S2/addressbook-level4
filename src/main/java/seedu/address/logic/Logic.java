package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyActivity;

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

    /** Returns the filtered list of activities */
    ObservableList<ReadOnlyActivity> getFilteredActivityList();

    /** Returns the filtered list of events */
    ObservableList<ReadOnlyActivity> getFilteredEventList();

    /** Returns the filtered list of deadlines */
    ObservableList<ReadOnlyActivity> getFilteredDeadlineList();

    /** Returns the filtered list of floating tasks */
    ObservableList<ReadOnlyActivity> getFilteredTaskList();

}
