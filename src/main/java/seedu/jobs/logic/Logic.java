package seedu.jobs.logic;

import java.io.IOException;

import javafx.collections.ObservableList;
import seedu.jobs.logic.commands.CommandResult;
import seedu.jobs.logic.commands.exceptions.CommandException;
import seedu.jobs.model.task.ReadOnlyTask;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws IOException
     */
    CommandResult execute(String commandText) throws CommandException, IOException;

    /** Returns the filtered list of persons */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

}
