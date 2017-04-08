package org.teamstbf.yats.logic;

import org.teamstbf.yats.logic.commands.CommandResult;
import org.teamstbf.yats.logic.commands.exceptions.CommandException;
import org.teamstbf.yats.model.item.ReadOnlyEvent;

import javafx.collections.ObservableList;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText
     *            The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException
     *             If an error occurs during command execution.
     */
    CommandResult execute(String commandText) throws CommandException;

    /** Returns the filtered list of tasks */
    ObservableList<ReadOnlyEvent> getFilteredTaskList();

    /** Returns the filtered list of done tasks */
    ObservableList<ReadOnlyEvent> getTaskFilteredTaskList();

}
