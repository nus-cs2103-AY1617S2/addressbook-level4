package seedu.todolist.logic;

import javafx.collections.ObservableList;
import seedu.todolist.logic.commands.CommandResult;
import seedu.todolist.logic.commands.exceptions.CommandException;
import seedu.todolist.model.todo.ReadOnlyTodo;

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

    /** Returns the filtered list of todos */
    ObservableList<ReadOnlyTodo> getFilteredTodoList();

}
