package t16b4.yats.logic;

import javafx.collections.ObservableList;
import t16b4.yats.logic.commands.CommandResult;
import t16b4.yats.logic.commands.exceptions.CommandException;
import t16b4.yats.model.item.ReadOnlyItem;

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

    /** Returns the filtered list of persons */
    ObservableList<ReadOnlyItem> getFilteredPersonList();

}
