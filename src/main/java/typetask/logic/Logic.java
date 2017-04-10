package typetask.logic;

import java.io.IOException;

import javafx.collections.ObservableList;
import typetask.commons.exceptions.DataConversionException;
import typetask.logic.commands.CommandResult;
import typetask.logic.commands.exceptions.CommandException;
import typetask.model.task.ReadOnlyTask;


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
     * @throws DataConversionException
     */
    CommandResult execute(String commandText) throws CommandException, IOException, DataConversionException;

    /** Returns the filtered list of tasks */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

}
